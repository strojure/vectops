(ns strojure.vectops.core)

(set! *warn-on-infer* true)

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(defprotocol VectorOps
  (insert-at [vec, idx, obj]
    "Inserts `obj` in the vector at index `idx`.")
  (remove-at [vec, idx]
    "Removes element from the vector at index `idx`."))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(defn transient-insert-loop
  "Implements `insert-at` loop over transient vector."
  [v!, idx, obj]
  (let [len (-count v!)]
    (loop [i idx, obj obj, v! v!]
      (if (== i len)
        (-conj! v! obj)
        (recur (inc i) (-nth v! i) (-assoc-n! v! i obj))))))

(defn transient-remove-loop
  "Implements `remove-at` loop over transient vector."
  [v!, idx]
  (let [end (dec (-count v!))]
    (loop [i idx, v! v!]
      (if (== i end)
        (-pop! v!)
        (let [k (inc i)]
          (recur k (-assoc-n! v! i (-nth v! k))))))))

(defn persistent-insert-at
  "Implements `insert-at` for persistent vector."
  [v, idx, obj]
  (let [len (-count v)]
    (if (== idx len)
      (-conj v obj)
      (loop [i idx, obj obj, res v]
        (if (== i len)
          (-conj res obj)
          (recur (inc i) (-nth res i) (-assoc-n res i obj)))))))

(defn persistent-remove-at
  "Implements `remove-at` for persistent vector."
  [v, idx]
  (let [end (dec (-count v))]
    (if (== idx end)
      (-pop v)
      (loop [i idx, res v]
        (if (== i end)
          (-pop res)
          (let [k (inc i)]
            (recur k (-assoc-n res i (-nth res k)))))))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

;; Editable persistent vectors.

(extend-type PersistentVector VectorOps
  (insert-at [this, idx, obj]
    (if (== idx (-count this))
      (-conj this obj)
      (let [m (-meta this)]
        (-> (-as-transient this)
            (transient-insert-loop idx obj)
            (-persistent!)
            (cond-> m (-with-meta m))))))
  (remove-at [this, idx]
    (if (== idx (unchecked-dec (-count this)))
      (-pop this)
      (let [m (-meta this)]
        (-> (-as-transient this)
            (transient-remove-loop idx)
            (-persistent!)
            (cond-> m (-with-meta m)))))))

;; Not-editable persistent vector implementations.
;; Every class should be listed explicitly, we cannot extend interfaces.

(extend-type Subvec VectorOps
  (insert-at [this, idx, obj] (persistent-insert-at this idx obj))
  (remove-at [this, idx] (persistent-remove-at this idx)))

;; Transient vectors.

(extend-type TransientVector VectorOps
  (insert-at [this, idx, obj]
    (if (== idx (-count this))
      (-conj! this obj)
      (transient-insert-loop this idx obj)))
  (remove-at [this, idx]
    (if (== idx (dec (-count this)))
      (-pop! this)
      (transient-remove-loop this idx))))

;; `nil` as empty vector.

(extend-type nil VectorOps
  (insert-at [_, idx, obj] (insert-at [] idx obj))
  (remove-at [_, idx] (remove-at [] idx)))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
