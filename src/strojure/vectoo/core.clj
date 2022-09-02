(ns strojure.vectoo.core
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

(defn transient-insert-at
  "Implements `insert-at` for transient vector."
  [^ITransientVector v!, ^long idx, obj]
  (let [len (.count v!)]
    (if (= idx len)
      (.conj v! obj)
      (loop [i idx, obj obj, v! v!]
        (if (= i len)
          (.conj v! obj)
          (let [next-obj (.nth v! (unchecked-int i))]
            (recur (unchecked-inc i) next-obj
                   (.assocN v! (unchecked-int i) obj))))))))

(defn transient-remove-at
  "Implements `remove-at` for transient vector."
  [^ITransientVector v!, ^long idx]
  (let [last-i (unchecked-dec (.count v!))]
    (if (= idx last-i)
      (.pop v!)
      (loop [i idx, v! v!]
        (if (= i last-i)
          (.pop v!)
          (let [k (unchecked-inc i)]
            (recur k (.assocN v! (unchecked-int i) (.nth v! (unchecked-int k))))))))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

;; Build persistent vector using transient collection.

(extend-type PersistentVector VectorOps

  (insert-at [v, ^long idx, obj]
    (if (= idx (.count v))
      (.cons v obj)
      (let [m (.meta v)]
        (-> (.asTransient v)
            (transient-insert-at idx obj)
            (persistent!)
            (cond-> m (with-meta m))))))

  (remove-at [v, ^long idx]
    (if (= idx (unchecked-dec (.count v)))
      (.pop v)
      (let [m (.meta v)]
        (-> (.asTransient v)
            (transient-remove-at idx)
            (persistent!)
            (cond-> m (with-meta m)))))))

;; Build other persistent vector implementations without transient collections.

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
    (let [last-i (unchecked-dec (.count v))]
      (if (= idx last-i)
        (.pop v)
        (loop [i idx, v v]
          (if (= i last-i)
            (let [m (meta v)]
              (cond-> (.pop v) m (with-meta m)))
            (let [k (unchecked-inc i)]
              (recur k (.assocN v (unchecked-int i) (.nth v (unchecked-int k)))))))))))

(extend-type ITransientVector VectorOps
  (insert-at [this, ^long idx, obj] (transient-insert-at this idx obj))
  (remove-at [this, idx] (transient-remove-at this idx)))

(extend-type nil VectorOps
  (insert-at [_, idx, obj] (insert-at [] idx obj))
  (remove-at [_, idx] (remove-at [] idx)))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
