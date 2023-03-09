(ns readme.usage.swap-at
  (:require [strojure.vectops.core :as vec]))

(vec/swap-at [0 1 2 3 4] 1 3)
#_[0 3 2 1 4]

(vec/swap-at [0 1 2 3 4] 0 5)
;; Execution error (IndexOutOfBoundsException)
