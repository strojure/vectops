(ns benchmarks.swap-at
  (:require [strojure.vectops.core :as vec]))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(def ^:private v10 (into [] (range 10)))

(def ^:private v1000 (into [] (range 1000)))

(defn- simple-swap-at
  [vct i j]
  (-> vct (assoc i (vct j)) (assoc j (vct i))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
;;; Short vector

(vec/swap-at v10 0 9)
;Evaluation count : 14561208 in 6 samples of 2426868 calls.
;             Execution time mean : 43,880495 ns
;    Execution time std-deviation : 5,985810 ns
;   Execution time lower quantile : 36,646652 ns ( 2,5%)
;   Execution time upper quantile : 49,920550 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 2.016400 msecs

(simple-swap-at v10 0 9)
;Evaluation count : 11977674 in 6 samples of 1996279 calls.
;             Execution time mean : 51,925066 ns
;    Execution time std-deviation : 6,830615 ns
;   Execution time lower quantile : 42,839446 ns ( 2,5%)
;   Execution time upper quantile : 58,319653 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 2.471000 msecs

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
;;; Long vector

(vec/swap-at v1000 0 999)
;Evaluation count : 7948134 in 6 samples of 1324689 calls.
;             Execution time mean : 104,246903 ns
;    Execution time std-deviation : 25,399560 ns
;   Execution time lower quantile : 79,006602 ns ( 2,5%)
;   Execution time upper quantile : 135,763744 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 3.035900 msecs

(simple-swap-at v1000 0 999)
;Evaluation count : 7048686 in 6 samples of 1174781 calls.
;             Execution time mean : 105,461117 ns
;    Execution time std-deviation : 23,782626 ns
;   Execution time lower quantile : 77,802601 ns ( 2,5%)
;   Execution time upper quantile : 136,055330 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 3.076700 msecs
