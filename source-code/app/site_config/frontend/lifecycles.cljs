
(ns app.site-config.frontend.lifecycles
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:home/add-menu-item! {:group    :website
                                       :icon     :settings
                                       :label    :settings
                                       :on-click [:router/go-to! "/@app-home/site-config"]}]})
