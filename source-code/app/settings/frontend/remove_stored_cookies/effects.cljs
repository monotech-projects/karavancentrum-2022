
(ns app.settings.frontend.remove-stored-cookies.effects
    (:require [settings.remove-stored-cookies.views :as remove-stored-cookies.views]
              [x.app-core.api                       :as a :refer [r]]
              [x.app-dictionary.api                 :as dictionary]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :settings/remove-stored-cookies!
  (fn [{:keys [db]} _]
      (let [message (r dictionary/look-up db :just-a-moment...)]
           {:dispatch-later [{:ms   0 :dispatch [:ui/set-shield! {:content message}]}
                             {:ms  50 :dispatch [:environment/remove-cookies!]}
                             {:ms 500 :dispatch [:boot-loader/refresh-app!]}]})))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :settings.remove-stored-cookies/render-dialog!
  [:ui/render-popup! :settings.remove-stored-cookies/view
                     {:body   #'remove-stored-cookies.views/body
                      :header #'remove-stored-cookies.views/header}])
