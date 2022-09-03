(ns strojure.vectops.core
  (:import (clojure.lang IPersistentVector ITransientVector PersistentVector)))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(defprotocol VectorOps
  (insert-at [vec, idx, obj]
    "Inserts `obj` in the vector at index `idx`.")
  (remove-at [vec, idx]
    "Removes element from the vector at index `idx`."))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(defn transient-insert-loop
  "Implements `insert-at` loop over transient vector."
  [^ITransientVector v!, ^long idx, obj]
  (let [len (.count v!)]
    (loop [i idx, obj obj, v! v!]
      (if (= i len)
        (.conj v! obj)
        (recur (unchecked-inc i) (.nth v! (unchecked-int i))
               (.assocN v! (unchecked-int i) obj))))))

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
  (insert-at [this, ^long idx, obj]
    (if (= idx (.count this))
      (.cons this obj)
      (let [m (.meta this)]
        (-> (.asTransient this)
            (transient-insert-loop idx obj)
            (persistent!)
            (cond-> m (with-meta m))))))
  (remove-at [this, ^long idx]
    (if (= idx (unchecked-dec (.count this)))
      (.pop this)
      (let [m (.meta this)]
        (-> (.asTransient this)
            (transient-remove-loop idx)
            (persistent!)
            (cond-> m (with-meta m)))))))

;; Not-editable persistent vector implementations.

(extend-type IPersistentVector VectorOps
  (insert-at [this, ^long idx, obj]
    (let [len (.count this)]
      (if (= idx len)
        (.cons this obj)
        (loop [i idx, obj obj, res this]
          (if (= i len)
            (.cons res obj)
            (recur (unchecked-inc i) (.nth res (unchecked-int i))
                   (.assocN res (unchecked-int i) obj)))))))
  (remove-at [this, ^long idx]
    (let [end (unchecked-dec (.count this))]
      (if (= idx end)
        (.pop this)
        (loop [i idx, res this]
          (if (= i end)
            (.pop res)
            (let [k (unchecked-inc i)]
              (recur k (.assocN res (unchecked-int i) (.nth res (unchecked-int k)))))))))))

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
