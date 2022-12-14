
(ns app.views.frontend.error-screen.effects
    (:require [app.views.frontend.error-screen.config :as error-screen.config]
              [app.views.frontend.error-screen.views  :as error-screen.views]
              [x.app-core.api                         :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :views.error-screen/render!
  ; @param (keyword) error-id
  ;  :no-connection, :no-permission, :page-not-found, :under-construction, :under-maintenance
  (fn [_ [_ error-id]]
      (let [content-props (get error-screen.config/ERROR-CONTENT error-id)]
           {:dispatch-n [[:ui/restore-default-window-title!]
                         [:ui/render-surface! :views.error-screen/view
                                              {:content [error-screen.views/view :views.error-screen/view content-props]}]]})))
