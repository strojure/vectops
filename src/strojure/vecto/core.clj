(ns strojure.vecto.core
  (:import (clojure.lang ITransientCollection Indexed PersistentVector)))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(defn insert
  "Inserts `obj` in the collection at index `idx` returning persistent vector."
  {:arglists '([coll idx obj])}
  [^Indexed coll, ^long idx, obj]
  (let [len (.count coll)]
    (if (= idx len)
      (conj coll obj)
      (let [xi (.nth coll (unchecked-int idx))]
        (loop [i 0, ^ITransientCollection v! (transient [])]
          (cond
            (= i len) (let [m (meta coll)]
                        (cond-> (.persistent v!) m (with-meta m)))
            (= i idx) (recur (unchecked-inc i) (-> v! (.conj obj) (.conj xi)))
            :else,,,, (recur (unchecked-inc i) (-> v! (.conj (.nth coll (unchecked-int i)))))))))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(defn remove-at
  "Removes element from the the collection at index `idx` returning persistent
  vector."
  {:arglists '([coll idx])}
  [^PersistentVector v, ^long idx]
  (let [last-i (unchecked-dec (.count v))]
    (if (= idx last-i)
      (.pop v)
      (loop [i idx, v! (.pop (.asTransient v))]
        (if (= i last-i)
          (let [m (.meta v)]
            (cond-> (.persistent v!) m (with-meta m)))
          (let [k (unchecked-inc i)]
            (recur k (.assocN v! (unchecked-int i) (.nth v (unchecked-int k))))))))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
