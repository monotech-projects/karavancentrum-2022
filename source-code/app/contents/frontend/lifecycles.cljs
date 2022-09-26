
(ns app.contents.frontend.lifecycles
    (:require [app.contents.frontend.dictionary :as dictionary]
              [x.app-core.api                   :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:dictionary/add-terms! dictionary/BOOK]})