
(ns app.settings.frontend.privacy.lifecycles
    (:require [app.home.frontend.api]
              [x.app-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-app-boot [:home.screen/add-menu-item! {:disabled?   true
                                              :group       :settings
                                              :icon        :security
                                              :icon-color  "#584b64"
                                              :icon-family :material-icons-outlined
                                              :label       :privacy
                                              :on-click    [:router/go-to! "/@app-home/settings/privacy"]
                                              :horizontal-weight 3}]})