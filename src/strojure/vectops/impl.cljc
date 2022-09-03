(ns strojure.vectops.impl
  #?(:clj (:import (clojure.lang ITransientVector))))

#?(:clj  (set! *warn-on-reflection* true)
   :cljs (set! *warn-on-infer* true))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(defn transient-insert-loop
  "Implements `insert-at` loop over transient vector."
  #?@(:clj
      ([^ITransientVector v!, ^long idx, obj]
       (let [len (.count v!)]
         (loop [i idx, obj obj, v! v!]
           (if (== i len)
             (.conj v! obj)
             (recur (unchecked-inc i) (.nth v! (unchecked-int i))
                    (.assocN v! (unchecked-int i) obj))))))
      :cljs
      ([v!, idx, obj]
       (let [len (-count v!)]
         (loop [i idx, obj obj, v! v!]
           (if (== i len)
             (-conj! v! obj)
             (recur (inc i) (-nth v! i) (-assoc-n! v! i obj))))))))

(defn transient-remove-loop
  "Implements `remove-at` loop over transient vector."
  #?@(:clj
      ([^ITransientVector v!, ^long idx]
       (let [end (unchecked-dec (.count v!))]
         (loop [i idx, v! v!]
           (if (== i end)
             (.pop v!)
             (let [k (unchecked-inc i)]
               (recur k (.assocN v! (unchecked-int i) (.nth v! (unchecked-int k)))))))))
      :cljs
      ([v!, idx]
       (let [end (dec (-count v!))]
         (loop [i idx, v! v!]
           (if (== i end)
             (-pop! v!)
             (let [k (inc i)]
               (recur k (-assoc-n! v! i (-nth v! k))))))))))

#?(:cljs
   (defn persistent-insert-at
     "Implements `insert-at` for persistent vector."
     [v, idx, obj]
     (let [len (-count v)]
       (if (== idx len)
         (-conj v obj)
         (loop [i idx, obj obj, res v]
           (if (== i len)
             (-conj res obj)
             (recur (inc i) (-nth res i) (-assoc-n res i obj))))))))

#?(:cljs
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
               (recur k (-assoc-n res i (-nth res k))))))))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
