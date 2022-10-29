
(ns site.components.frontend.navbar.helpers
    (:require [x.app-environment.api :as x.environment]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn scroll-f
  ; @param (boolean) intersecting?
  [intersecting?]
  (x.environment/set-element-attribute! "sc-navbar" "data-scrolled" (not intersecting?)))
