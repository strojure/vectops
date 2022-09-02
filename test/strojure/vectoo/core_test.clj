(ns strojure.vectoo.core-test
  (:require [clojure.test :as test :refer [deftest testing]]
            [strojure.vectoo.core :as vec])
  (:import (clojure.lang ITransientCollection)))

(set! *warn-on-reflection* true)

(declare thrown?)

(def ^:private x 'x)

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(deftest insert-test

  (testing "Insert element at index."
    (test/are [form] form
      (vector? (vec/insert-at [1 2 3 4 5] 2 x))
      (= [1 2 x 3 4 5] (vec/insert-at [1 2 3 4 5] 2 x))
      (= [x 1 2 3 4 5] (vec/insert-at [1 2 3 4 5] 0 x))
      (= [1 2 3 4 5 x] (vec/insert-at [1 2 3 4 5] 5 x))
      (= [x],,,,,,,,,, (vec/insert-at [] 0 x))
      (= [x],,,,,,,,,, (vec/insert-at nil 0 x))))

  (testing "Insert element out of bounds."
    (test/are [form] form
      (thrown? IndexOutOfBoundsException (vec/insert-at [1 2 3 4 5] -1 x))
      (thrown? IndexOutOfBoundsException (vec/insert-at [1 2 3 4 5] 6 x))
      (thrown? IndexOutOfBoundsException (vec/insert-at nil 1 x))))

  (testing "Preserve metadata on insert."
    (test/are [form] form
      (= {::meta true} (meta (vec/insert-at ^::meta [1 2 3 4 5] 2 x)))
      (= {::meta true} (meta (vec/insert-at ^::meta [1 2 3 4 5] 0 x)))
      (= {::meta true} (meta (vec/insert-at ^::meta [1 2 3 4 5] 5 x)))
      (= {::meta true} (meta (vec/insert-at ^::meta [] 0 x)))))

  (testing "Insert element in transient vector."
    (test/are [form] form
      (instance? ITransientCollection (vec/insert-at (transient [1 2 3 4 5]) 2 x))
      (= [1 2 x 3 4 5] (persistent! (vec/insert-at (transient [1 2 3 4 5]) 2 x)))
      (= [x 1 2 3 4 5] (persistent! (vec/insert-at (transient [1 2 3 4 5]) 0 x)))
      (= [1 2 3 4 5 x] (persistent! (vec/insert-at (transient [1 2 3 4 5]) 5 x)))
      (= [x],,,,,,,,,, (persistent! (vec/insert-at (transient []) 0 x)))
      (thrown? IndexOutOfBoundsException (vec/insert-at (transient [1 2 3 4 5]) -1 x))
      (thrown? IndexOutOfBoundsException (vec/insert-at (transient [1 2 3 4 5]) 6 x))))
  )

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(deftest remove-test

  (testing "Remove element at index."
    (test/are [form] form
      (vector? (vec/remove-at [1 2 3 4 5] 2))
      (= [2 3 4 5] (vec/remove-at [1 2 3 4 5] 0))
      (= [1 2 3 4] (vec/remove-at [1 2 3 4 5] 4))
      (= [1 2 4 5] (vec/remove-at [1 2 3 4 5] 2))
      (= [],,,,,,, (vec/remove-at [1] 0))))

  (testing "Remove element out of bounds."
    (test/are [form] form
      (thrown? IndexOutOfBoundsException (vec/remove-at [1 2 3 4 5] -1))
      (thrown? IndexOutOfBoundsException (vec/remove-at [1 2 3 4 5] 5))
      (thrown? IndexOutOfBoundsException (vec/remove-at nil 0))))

  (testing "Preserve metadata on remove."
    (test/are [form] form
      (= {::meta true} (meta (vec/remove-at ^::meta [1 2 3 4 5] 0)))
      (= {::meta true} (meta (vec/remove-at ^::meta [1 2 3 4 5] 4)))
      (= {::meta true} (meta (vec/remove-at ^::meta [1 2 3 4 5] 2)))
      (= {::meta true} (meta (vec/remove-at ^::meta [1] 0)))))

  (testing "Remove element in transient vector."
    (test/are [form] form
      (instance? ITransientCollection (vec/remove-at (transient [1 2 3 4 5]) 2))
      (= [2 3 4 5] (persistent! (vec/remove-at (transient [1 2 3 4 5]) 0)))
      (= [1 2 3 4] (persistent! (vec/remove-at (transient [1 2 3 4 5]) 4)))
      (= [1 2 4 5] (persistent! (vec/remove-at (transient [1 2 3 4 5]) 2)))
      (= [],,,,,,, (persistent! (vec/remove-at (transient [1]) 0)))
      (thrown? IndexOutOfBoundsException (vec/remove-at (transient [1 2 3 4 5]) -1))
      (thrown? IndexOutOfBoundsException (vec/remove-at (transient [1 2 3 4 5]) 5))))
  )

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
