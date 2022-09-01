(ns strojure.vecto.core
  (:import (clojure.lang ITransientCollection Indexed)))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(defn insert
  "Inserts `obj` in the collection at index `idx` returning persistent vector."
  {:arglists '([coll at obj])}
  [^Indexed coll, ^long idx, obj]
  (let [len (.count coll)]
    (if (= idx len)
      (conj coll obj)
      (let [xi (.nth coll (unchecked-int idx))]
        (loop [k 0, ^ITransientCollection v! (transient [])]
          (cond
            (= k len) (let [m (meta coll)]
                        (cond-> (.persistent v!) m (with-meta m)))
            (= k idx) (recur (unchecked-inc k) (-> v! (.conj obj) (.conj xi)))
            :else,,,, (recur (unchecked-inc k) (-> v! (.conj (.nth coll (unchecked-int k)))))))))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
