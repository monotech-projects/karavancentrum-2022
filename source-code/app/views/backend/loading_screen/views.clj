
(ns app.views.backend.loading-screen.views
    (:require [mid-fruits.candy  :refer [param]]
              [mid-fruits.css    :as css]
              [x.server-core.api :as a]
              [x.server-user.api :as user]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- app-title
  [request]
  (let [title @(a/subscribe [:core/get-app-config-item :app-title])]
       [:div {:style (css/parse {:font-size      (css/var "font-size-xs")
                                 :font-weight    "600"
                                 :padding        "12px 0"
                                 :text-align     "center"
                                 :text-transform "uppercase"
                                 :user-select    "none"
                                 :-moz-user-select    "none"
                                 :-ms-user-select     "none"
                                 :-webkit-user-select "none"})}
             (param title)]))

(defn- app-logo
  [request]
  (let [selected-theme (user/request->user-settings-item request :selected-theme)]
       [:div {:style (css/parse {:background-image (case selected-theme :light (css/url "/logo/logo-light.png")
                                                                        :dark  (css/url "/logo/logo-dark.png"))
                                 :background-position "center"
                                 :background-repeat   "no-repeat"
                                 :background-size     "contain"
                                 :height              "100px"
                                 :overflow            "hidden"
                                 :width               "100px"})}]))

(defn view
  [request]
  [:div {}
        (app-logo  request)
        (app-title request)])
