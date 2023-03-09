(ns readme.usage.remove-at
  (:require [strojure.vectops.core :as vec]))

(vec/remove-at [0 1 2 3 4] 0)
#_=> [1 2 3 4]

(vec/remove-at [0 1 2 3 4] 2)
#_=> [0 1 3 4]

(vec/remove-at [0 1 2 3 4] 4)
#_=> [0 1 2 3]

(vec/remove-at [0 1 2 3 4] 5)
;; Execution error (IndexOutOfBoundsException)
