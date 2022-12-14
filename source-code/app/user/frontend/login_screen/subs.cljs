
(ns app.user.frontend.login-screen.subs
    (:require [mid-fruits.string :as string]
              [x.app-core.api    :as a :refer [r]]
              [x.app-sync.api    :as sync]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn login-fields-unfilled?
  [db _]
  (not (and (string/nonempty? (get-in db [:user :login-screen/data-items :email-address]))
            (string/nonempty? (get-in db [:user :login-screen/data-items :password])))))

(defn login-button-disabled?
  [db _]
  (or (r login-fields-unfilled?     db)
      (r sync/listening-to-request? db :user/authenticate!))
  ; BUG#4677
  ; A Chrome böngésző nem írja bele az autofill értékeket az input mezőkbe, csak kirendereli rajtuk,
  ; ezért amíg az első valós on-mouse-down esemény nem történik meg, addig a mezők értéke nil,
  ; és ilyenkor úgy látszódik, mint ha ki lennének töltve a mezők és közben a login gomb disabled
  ; állapotban van ...
  (r sync/listening-to-request? db :user/authenticate!))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-sub :user.login-screen/login-button-disabled? login-button-disabled?)
