(ns strojure.vectops.core
  (:import (clojure.lang IPersistentVector ITransientVector PersistentVector)))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(defprotocol VectorOps
  (insert-at [coll, idx, obj]
    "Inserts `obj` in the indexed collection at index `idx`.")
  (remove-at [coll, idx]
    "Removes element from the indexed collection at index `idx`."))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(defn transient-insert-loop
  "Implements `insert-at` loop over transient vector."
  [^ITransientVector v!, ^long idx, obj]
  (let [len (.count v!)]
    (loop [i idx, obj obj, v! v!]
      (if (= i len)
        (.conj v! obj)
        (let [next-obj (.nth v! (unchecked-int i))]
          (recur (unchecked-inc i) next-obj
                 (.assocN v! (unchecked-int i) obj)))))))

(defn transient-remove-loop
  "Implements `remove-at` loop over transient vector."
  [^ITransientVector v!, ^long idx]
  (let [end (unchecked-dec (.count v!))]
    (loop [i idx, v! v!]
      (if (= i end)
        (.pop v!)
        (let [k (unchecked-inc i)]
          (recur k (.assocN v! (unchecked-int i) (.nth v! (unchecked-int k)))))))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

;; Editable persistent vectors.

(extend-type PersistentVector VectorOps
  (insert-at [v, ^long idx, obj]
    (if (= idx (.count v))
      (.cons v obj)
      (let [m (.meta v)]
        (-> (.asTransient v)
            (transient-insert-loop idx obj)
            (persistent!)
            (cond-> m (with-meta m))))))
  (remove-at [v, ^long idx]
    (if (= idx (unchecked-dec (.count v)))
      (.pop v)
      (let [m (.meta v)]
        (-> (.asTransient v)
            (transient-remove-loop idx)
            (persistent!)
            (cond-> m (with-meta m)))))))

;; Not-editable persistent vector implementations.

(extend-type IPersistentVector VectorOps
  (insert-at [v, ^long idx, obj]
    (let [len (.count v)]
      (if (= idx len)
        (.cons v obj)
        (loop [i idx, obj obj, v v]
          (if (= i len)
            (let [m (meta v)]
              (cond-> (.cons v obj) m (with-meta m)))
            (let [next-obj (.nth v (unchecked-int i))]
              (recur (unchecked-inc i) next-obj
                     (.assocN v (unchecked-int i) obj))))))))
  (remove-at [v, ^long idx]
    (let [end (unchecked-dec (.count v))]
      (if (= idx end)
        (.pop v)
        (loop [i idx, v v]
          (if (= i end)
            (let [m (meta v)]
              (cond-> (.pop v) m (with-meta m)))
            (let [k (unchecked-inc i)]
              (recur k (.assocN v (unchecked-int i) (.nth v (unchecked-int k)))))))))))

;; Transient vectors.

(extend-type ITransientVector VectorOps
  (insert-at [this, ^long idx, obj]
    (if (= idx (.count this))
      (.conj this obj)
      (transient-insert-loop this idx obj)))
  (remove-at [this, ^long idx]
    (if (= idx (unchecked-dec (.count this)))
      (.pop this)
      (transient-remove-loop this idx))))

;; `nil` as empty vector.

(extend-type nil VectorOps
  (insert-at [_, idx, obj] (insert-at [] idx obj))
  (remove-at [_, idx] (remove-at [] idx)))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
