(ns benchmarks.remove-at
  (:require [strojure.vectops.core :as vec]))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(def ^:private v10 (into [] (range 10)))

(def ^:private v1000 (into [] (range 1000)))

(defn- simple-remove-at
  [vct idx]
  (into (subvec vct 0 idx)
        (subvec vct (inc idx))))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
;;; Short vector

;; remove first

(vec/remove-at v10 0)
;Evaluation count : 2115054 in 6 samples of 352509 calls.
;             Execution time mean : 288,186519 ns
;    Execution time std-deviation : 20,365911 ns
;   Execution time lower quantile : 273,411104 ns ( 2,5%)
;   Execution time upper quantile : 321,366937 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 7.080500 msecs

(simple-remove-at v10 0)
;Evaluation count : 1007034 in 6 samples of 167839 calls.
;             Execution time mean : 602,365547 ns
;    Execution time std-deviation : 9,998807 ns
;   Execution time lower quantile : 585,249441 ns ( 2,5%)
;   Execution time upper quantile : 612,930806 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 14.784600 msecs

;; remove in the middle

(vec/remove-at v10 5)
;valuation count : 2441514 in 6 samples of 406919 calls.
;             Execution time mean : 269,715170 ns
;    Execution time std-deviation : 19,951410 ns
;   Execution time lower quantile : 252,563518 ns ( 2,5%)
;   Execution time upper quantile : 299,287159 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 4.406100 msecs

(simple-remove-at v10 5)
;Evaluation count : 1570680 in 6 samples of 261780 calls.
;             Execution time mean : 384,452081 ns
;    Execution time std-deviation : 17,575655 ns
;   Execution time lower quantile : 361,071698 ns ( 2,5%)
;   Execution time upper quantile : 404,157495 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 7.292100 msecs

;; remove before last

(vec/remove-at v10 8)
;Evaluation count : 4503708 in 6 samples of 750618 calls.
;             Execution time mean : 156,589196 ns
;    Execution time std-deviation : 18,020757 ns
;   Execution time lower quantile : 132,655473 ns ( 2,5%)
;   Execution time upper quantile : 172,430998 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 2.714900 msecs

(simple-remove-at v10 8)
;Evaluation count : 2544900 in 6 samples of 424150 calls.
;             Execution time mean : 237,974310 ns
;    Execution time std-deviation : 5,677552 ns
;   Execution time lower quantile : 230,506748 ns ( 2,5%)
;   Execution time upper quantile : 243,352174 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 3.404900 msecs

;; remove last

(vec/remove-at v10 9)
;Evaluation count : 18701964 in 6 samples of 3116994 calls.
;             Execution time mean : 24,850252 ns
;    Execution time std-deviation : 4,673248 ns
;   Execution time lower quantile : 20,323482 ns ( 2,5%)
;   Execution time upper quantile : 31,399076 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 0.696200 msecs

(simple-remove-at v10 9)
;Evaluation count : 3183594 in 6 samples of 530599 calls.
;             Execution time mean : 186,565857 ns
;    Execution time std-deviation : 3,237990 ns
;   Execution time lower quantile : 183,383604 ns ( 2,5%)
;   Execution time upper quantile : 191,703871 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 2.038300 msecs

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
;;; Long vector

;; remove first

(vec/remove-at v1000 0)
;Evaluation count : 29340 in 6 samples of 4890 calls.
;             Execution time mean : 20,101866 µs
;    Execution time std-deviation : 453,625541 ns
;   Execution time lower quantile : 19,620680 µs ( 2,5%)
;   Execution time upper quantile : 20,588789 µs (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 831.207600 msecs

(simple-remove-at v1000 0)
;Evaluation count : 22818 in 6 samples of 3803 calls.
;             Execution time mean : 26,695353 µs
;    Execution time std-deviation : 489,563523 ns
;   Execution time lower quantile : 26,126219 µs ( 2,5%)
;   Execution time upper quantile : 27,208582 µs (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 2036.988500 msecs

;; remove in the middle

(vec/remove-at v1000 500)
;Evaluation count : 60780 in 6 samples of 10130 calls.
;             Execution time mean : 9,937497 µs
;    Execution time std-deviation : 129,663832 ns
;   Execution time lower quantile : 9,794570 µs ( 2,5%)
;   Execution time upper quantile : 10,085498 µs (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 406.679200 msecs

(simple-remove-at v1000 500)
;Evaluation count : 15780 in 6 samples of 2630 calls.
;             Execution time mean : 50,926154 µs
;    Execution time std-deviation : 6,870702 µs
;   Execution time lower quantile : 40,852130 µs ( 2,5%)
;   Execution time upper quantile : 58,288422 µs (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 1018.668700 msecs

;; remove before last

(vec/remove-at v1000 998)
;Evaluation count : 4319616 in 6 samples of 719936 calls.
;             Execution time mean : 149,378232 ns
;    Execution time std-deviation : 20,383352 ns
;   Execution time lower quantile : 131,352092 ns ( 2,5%)
;   Execution time upper quantile : 179,171412 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 2.754800 msecs

(simple-remove-at v1000 998)
;Evaluation count : 2437134 in 6 samples of 406189 calls.
;             Execution time mean : 251,227658 ns
;    Execution time std-deviation : 17,382206 ns
;   Execution time lower quantile : 239,457398 ns ( 2,5%)
;   Execution time upper quantile : 280,701899 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 3.350800 msecs

;; remove last

(vec/remove-at v1000 999)
;Evaluation count : 21422130 in 6 samples of 3570355 calls.
;             Execution time mean : 24,335134 ns
;    Execution time std-deviation : 4,050445 ns
;   Execution time lower quantile : 19,592062 ns ( 2,5%)
;   Execution time upper quantile : 30,580518 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 0.676800 msecs

(simple-remove-at v1000 999)
;Evaluation count : 3099240 in 6 samples of 516540 calls.
;             Execution time mean : 187,658589 ns
;    Execution time std-deviation : 2,903811 ns
;   Execution time lower quantile : 184,803082 ns ( 2,5%)
;   Execution time upper quantile : 190,883468 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 2.415000 msecs
