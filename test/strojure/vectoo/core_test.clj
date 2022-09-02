(ns strojure.vectoo.core-test
  (:require [clojure.test :as test :refer [deftest testing]]
            [strojure.vectoo.core :as vec]))

(set! *warn-on-reflection* true)

(declare thrown?)

(def ^:private x 'x)

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(deftest insert-test

  (testing "Insert element at index."
    (test/are [form] form
      (vector?,,,,,,,, (vec/insert-at [1 2 3 4 5] 2 x))
      (= [1 2 x 3 4 5] (vec/insert-at [1 2 3 4 5] 2 x))
      (= [x 1 2 3 4 5] (vec/insert-at [1 2 3 4 5] 0 x))
      (= [1 2 3 4 5 x] (vec/insert-at [1 2 3 4 5] 5 x))
      (= [x],,,,,,,,,, (vec/insert-at [] 0 x))))

  (testing "Insert element out of bounds."
    (test/are [form] form
      (thrown? IndexOutOfBoundsException (vec/insert-at [1 2 3 4 5] -1 x))
      (thrown? IndexOutOfBoundsException (vec/insert-at [1 2 3 4 5] 6 x))))

  (testing "Preserve metadata on insert."
    (test/are [form] form
      (= {::meta true} (meta (vec/insert-at ^::meta [1 2 3 4 5] 2 x)))
      (= {::meta true} (meta (vec/insert-at ^::meta [1 2 3 4 5] 0 x)))
      (= {::meta true} (meta (vec/insert-at ^::meta [1 2 3 4 5] 5 x)))
      (= {::meta true} (meta (vec/insert-at ^::meta [] 0 x)))))

  )

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(deftest remove-test

  (testing "Removing element at index."
    (test/are [form] form
      (vector?,,,, (vec/remove-at [1 2 3 4 5] 0))
      (= [2 3 4 5] (vec/remove-at [1 2 3 4 5] 0))
      (= [1 2 3 4] (vec/remove-at [1 2 3 4 5] 4))
      (= [1 2 4 5] (vec/remove-at [1 2 3 4 5] 2))
      (= [],,,,,,, (vec/remove-at [1] 0))))

  (testing "Removing element out of bounds."
    (test/are [form] form
      (thrown? IndexOutOfBoundsException (vec/remove-at [1 2 3 4 5] -1))
      (thrown? IndexOutOfBoundsException (vec/remove-at [1 2 3 4 5] 5))))

  (testing "Preserve metadata on remove."
    (test/are [form] form
      (= {::meta true} (meta (vec/remove-at ^::meta [1 2 3 4 5] 0)))
      (= {::meta true} (meta (vec/remove-at ^::meta [1 2 3 4 5] 4)))
      (= {::meta true} (meta (vec/remove-at ^::meta [1 2 3 4 5] 2)))
      (= {::meta true} (meta (vec/remove-at ^::meta [1] 0)))))

  )

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
