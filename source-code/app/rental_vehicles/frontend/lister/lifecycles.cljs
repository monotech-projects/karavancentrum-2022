
(ns app.rental-vehicles.frontend.lister.lifecycles
    (:require [x.app-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-app-boot [:home.screen/add-menu-item! {:group       :vehicles
                                              :icon        :airport_shuttle
                                              :icon-color  "#6a7e8e"
                                              :icon-family :material-icons-outlined
                                              :label       :rental-vehicles
                                              :on-click    [:router/go-to! "/@app-home/rental-vehicles"]}]})