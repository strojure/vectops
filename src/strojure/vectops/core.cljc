(ns strojure.vectops.core
  #?(:clj (:import (clojure.lang IEditableCollection IPersistentVector ITransientVector))))

#?(:clj (set! *warn-on-reflection* true) :cljs (set! *warn-on-infer* true))
#?(:clj (set! *unchecked-math* :warn-on-boxed))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

;;; insert-at

(defn- transient-insert-loop
  #?@(:clj
      ([^ITransientVector vct!, ^long idx, obj]
       (let [len (.count vct!)]
         (loop [i idx, obj obj, res! vct!]
           (if (== i len)
             (.conj res! obj)
             (recur (unchecked-inc i) (.nth res! (unchecked-int i))
                    (.assocN res! (unchecked-int i) obj))))))
      :cljs
      ([vct!, idx, obj]
       (let [len (-count vct!)]
         (loop [i idx, obj obj, res! vct!]
           (if (== i len)
             (-conj! res! obj)
             (recur (inc i) (-nth res! i) (-assoc-n! res! i obj))))))))

(defn- editable-insert-at
  [vct, ^long idx, obj]
  (let [m (meta vct)]
    (-> (transient vct)
        (transient-insert-loop idx obj)
        (persistent!)
        (cond-> m (with-meta m)))))

(defn insert-at
  "Inserts `obj` in persistent vector at index `idx`."
  #?@(:clj
      ([^IPersistentVector vct, ^long idx, obj]
       (let [vct (or vct []), len (.count vct)]
         (cond
           (== idx len) (.cons vct obj)
           (instance? IEditableCollection vct) (editable-insert-at vct idx obj)
           :else (loop [i idx, obj obj, res vct]
                   (if (== i len)
                     (.cons res obj)
                     (recur (unchecked-inc i) (.nth res (unchecked-int i))
                            (.assocN res (unchecked-int i) obj)))))))
      :cljs
      ([vct idx obj]
       (let [vct (or vct []), len (-count vct)]
         (cond
           (== idx len) (-conj vct obj)
           (implements? IEditableCollection vct) (editable-insert-at vct idx obj)
           :else (loop [i idx, obj obj, res vct]
                   (if (== i len)
                     (-conj res obj)
                     (recur (inc i) (-nth res i) (-assoc-n res i obj)))))))))

(defn insert-at!
  "Inserts `obj` in transient vector at index `idx`."
  #?@(:clj
      ([^ITransientVector vct!, ^long idx, obj]
       (if (== idx (.count vct!))
         (.conj vct! obj)
         (transient-insert-loop vct! idx obj)))
      :cljs
      ([vct, idx, obj]
       (if (== idx (-count vct))
         (-conj! vct obj)
         (transient-insert-loop vct idx obj)))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

;;; remove-at

(defn- transient-remove-loop
  #?@(:clj
      ([^ITransientVector vct!, ^long idx]
       (let [end (unchecked-dec (.count vct!))]
         (loop [i idx, res! vct!]
           (if (== i end)
             (.pop res!)
             (let [k (unchecked-inc i)]
               (recur k (.assocN res! (unchecked-int i) (.nth res! (unchecked-int k)))))))))
      :cljs
      ([vct!, idx]
       (let [end (dec (-count vct!))]
         (loop [i idx, res! vct!]
           (if (== i end)
             (-pop! res!)
             (let [k (inc i)]
               (recur k (-assoc-n! res! i (-nth res! k))))))))))

(defn- editable-remove-at
  [vct, ^long idx]
  (let [m (meta vct)]
    (-> (transient vct)
        (transient-remove-loop idx)
        (persistent!)
        (cond-> m (with-meta m)))))

(defn remove-at
  "Removes element from persistent vector at index `idx`."
  #?@(:clj
      ([^IPersistentVector vct, ^long idx]
       (let [vct (or vct []), end (unchecked-dec (.count vct))]
         (cond
           (== idx end) (.pop vct)
           (instance? IEditableCollection vct) (editable-remove-at vct idx)
           :else (loop [i idx, res vct]
                   (if (== i end)
                     (.pop res)
                     (let [k (unchecked-inc i)]
                       (recur k (.assocN res (unchecked-int i) (.nth res (unchecked-int k))))))))))
      :cljs
      ([vct idx]
       (let [vct (or vct []), end (dec (-count vct))]
         (cond
           (== idx end) (-pop vct)
           (implements? IEditableCollection vct) (editable-remove-at vct idx)
           :else (loop [i idx, res vct]
                   (if (== i end)
                     (-pop res)
                     (let [k (inc i)]
                       (recur k (-assoc-n res i (-nth res k)))))))))))

(defn remove-at!
  "Removes element from transient vector at index `idx`."
  #?@(:clj
      ([^ITransientVector vct!, ^long idx]
       (if (== idx (unchecked-dec (.count vct!)))
         (.pop vct!)
         (transient-remove-loop vct! idx)))
      :cljs
      ([vct!, idx]
       (if (== idx (dec (-count vct!)))
         (-pop! vct!)
         (transient-remove-loop vct! idx)))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

;;; swap-at

(defn swap-at
  "Swaps elements in persistent vector at indexes `i` and `j`."
  #?@(:clj
      ([^IPersistentVector vct, ^long i, ^long j]
       (-> (or vct [])
           (.assocN (unchecked-int i) (.nth vct (unchecked-int j)))
           (.assocN (unchecked-int j) (.nth vct (unchecked-int i)))))
      :cljs
      ([vct i j]
       (-> (or vct [])
           (-assoc-n i (-nth vct j))
           (-assoc-n j (-nth vct i))))))

(defn swap-at!
  "Swaps elements in transient vector at indexes `i` and `j`."
  #?@(:clj
      ([^ITransientVector vct!, ^long i, ^long j]
       (let [vi (.nth vct! (unchecked-int i))
             vj (.nth vct! (unchecked-int j))]
         (-> vct!
             (.assocN (unchecked-int i) vj)
             (.assocN (unchecked-int j) vi))))
      :cljs
      ([vct! i j]
       (let [vi (-nth vct! i)
             vj (-nth vct! j)]
         (-> vct!
             (-assoc-n! j vi)
             (-assoc-n! i vj))))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
