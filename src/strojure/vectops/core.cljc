(ns strojure.vectops.core
  (:require [strojure.vectops.impl :as impl])
  #?(:clj (:import (clojure.lang IPersistentVector ITransientVector PersistentVector))))

#?(:clj (set! *warn-on-reflection* true) :cljs (set! *warn-on-infer* true))
#?(:clj (set! *unchecked-math* :warn-on-boxed))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(defprotocol VectorOps
  (insert-at [vec, idx, obj]
    "Inserts `obj` in the vector at index `idx`.")
  (remove-at [vec, idx]
    "Removes element from the vector at index `idx`."))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

;;; Editable persistent vectors.

#?(:clj
   (extend-type PersistentVector VectorOps
     (insert-at [this, ^long idx, obj]
       (if (== idx (.count this))
         (.cons this obj)
         (let [m (.meta this)]
           (-> (.asTransient this)
               (impl/transient-insert-loop idx obj)
               (persistent!)
               (cond-> m (with-meta m))))))
     (remove-at [this, ^long idx]
       (if (== idx (unchecked-dec (.count this)))
         (.pop this)
         (let [m (.meta this)]
           (-> (.asTransient this)
               (impl/transient-remove-loop idx)
               (persistent!)
               (cond-> m (with-meta m))))))))

#?(:cljs
   (extend-type PersistentVector VectorOps
     (insert-at [this, idx, obj]
       (if (== idx (-count this))
         (-conj this obj)
         (let [m (-meta this)]
           (-> (-as-transient this)
               (impl/transient-insert-loop idx obj)
               (-persistent!)
               (cond-> m (-with-meta m))))))
     (remove-at [this, idx]
       (if (== idx (unchecked-dec (-count this)))
         (-pop this)
         (let [m (-meta this)]
           (-> (-as-transient this)
               (impl/transient-remove-loop idx)
               (-persistent!)
               (cond-> m (-with-meta m))))))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

;;; Not-editable persistent vector implementations.

#?(:clj
   (extend-type IPersistentVector VectorOps
     (insert-at [this, ^long idx, obj]
       (let [len (.count this)]
         (if (== idx len)
           (.cons this obj)
           (loop [i idx, obj obj, res this]
             (if (== i len)
               (.cons res obj)
               (recur (unchecked-inc i) (.nth res (unchecked-int i))
                      (.assocN res (unchecked-int i) obj)))))))
     (remove-at [this, ^long idx]
       (let [end (unchecked-dec (.count this))]
         (if (== idx end)
           (.pop this)
           (loop [i idx, res this]
             (if (== i end)
               (.pop res)
               (let [k (unchecked-inc i)]
                 (recur k (.assocN res (unchecked-int i) (.nth res (unchecked-int k))))))))))))

;; Every class should be listed explicitly, we cannot extend interfaces.

#?(:cljs
   (extend-type Subvec VectorOps
     (insert-at [this, idx, obj] (impl/persistent-insert-at this idx obj))
     (remove-at [this, idx] (impl/persistent-remove-at this idx))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

;;; Transient vectors.

#?(:clj
   (extend-type ITransientVector VectorOps
     (insert-at [this, ^long idx, obj]
       (if (== idx (.count this))
         (.conj this obj)
         (impl/transient-insert-loop this idx obj)))
     (remove-at [this, ^long idx]
       (if (== idx (unchecked-dec (.count this)))
         (.pop this)
         (impl/transient-remove-loop this idx)))))

#?(:cljs
   (extend-type TransientVector VectorOps
     (insert-at [this, idx, obj]
       (if (== idx (-count this))
         (-conj! this obj)
         (impl/transient-insert-loop this idx obj)))
     (remove-at [this, idx]
       (if (== idx (dec (-count this)))
         (-pop! this)
         (impl/transient-remove-loop this idx)))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

;;; `nil` as empty vector.

(extend-type nil VectorOps
  (insert-at [_, idx, obj] (insert-at [] idx obj))
  (remove-at [_, idx] (remove-at [] idx)))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
