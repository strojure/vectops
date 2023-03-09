# vectops

Basic operations with Clojure(Script) vectors.

[![Clojars Project](https://img.shields.io/clojars/v/com.github.strojure/vectops.svg)](https://clojars.org/com.github.strojure/vectops)

## Purpose

* Provide functions for basic operations with persistent vectors like inserting
  or removing elements at index.
* Performant implementation without extra dependencies.
* Support Clojure and ClojureScript.

## Usage

### insert-at

The function `insert-at` inserts element in persistent vector at specified
index. There is also `insert-at!` function for transient vectors.

```clojure
(ns readme.usage.insert-at
  (:require [strojure.vectops.core :as vec]))

(vec/insert-at [0 1 2 3 4] 0 :x)
#_=> [:x 0 1 2 3 4]

(vec/insert-at [0 1 2 3 4] 2 :x)
#_=> [0 1 :x 2 3 4]

(vec/insert-at [0 1 2 3 4] 5 :x)
#_=> [0 1 2 3 4 :x]

(vec/insert-at [0 1 2 3 4] 6 :x)
;; Execution error (IndexOutOfBoundsException)

(vec/insert-at nil 0 :x)
#_=> [:x]
```

### remove-at

The function `remove-at` removes element at specified index from persistent
vector. There is also `remove-at!` function for transient vectors.

```clojure
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
```

### swap-at

The function `swap-at` swaps elements in persistent vector at two indexes.
There is also `swap-at!` function for transient vectors.

```clojure
(ns readme.usage.swap-at
  (:require [strojure.vectops.core :as vec]))

(vec/swap-at [0 1 2 3 4] 1 3)
#_[0 3 2 1 4]

(vec/swap-at [0 1 2 3 4] 0 5)
;; Execution error (IndexOutOfBoundsException)
```

## Performance

* [vec/insert-at](doc/benchmarks/insert_at.cljc)

* [vec/remove-at](doc/benchmarks/remove_at.cljc)

* [vec/swap-at](doc/benchmarks/swap_at.cljc)
