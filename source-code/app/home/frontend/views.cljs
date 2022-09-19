
(ns app.home.frontend.views
    (:require [app.common.frontend.api  :as common]
              [app.home.frontend.config :as config]
              [layouts.surface-a.api    :as surface-a]
              [mid-fruits.css           :as css]
              [mid-fruits.vector        :as vector]
              [x.app-components.api     :as components]
              [x.app-core.api           :as a]
              [x.app-elements.api       :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- user-profile-picture
  []
  (let [user-profile-picture @(a/subscribe [:user/get-user-profile-picture])]
       [:div.x-user-profile-picture {:style {:backgroundImage     (css/url user-profile-picture)
                                             :background-color    (css/var "background-color-highlight")
                                             :border-radius       "50%";
                                             :background-position "center"
                                             :background-repeat   "no-repeat"
                                             :background-size     "90%"
                                             :overflow            "hidden"
                                             :height              "60px"
                                             :width               "60px"}}]))

(defn- user-name-label
  []
  (let [user-name @(a/subscribe [:user/get-user-name])]
       [elements/label ::user-name-label
                       {:content     user-name
                        :font-size   :s
                        :font-weight :extra-bold
                        :indent      {:left :xs}
                        :style       {:line-height "18px"}}]))

(defn- user-email-address-label
  []
  (let [user-email-address @(a/subscribe [:user/get-user-email-address])]
       [elements/label ::user-email-address-label
                       {:color     :muted
                        :content   user-email-address
                        :font-size :xs
                        :indent    {:left :xs}
                        :style     {:line-height "18px"}}]))

(defn- user-card
  []
  [:div {:style {:display :flex}}
        [:div {}
              [user-profile-picture]]
        [:div {:style {:display :flex :flex-direction :column :justify-content :center}}
              [user-name-label]
              [user-email-address-label]]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- breadcrumbs
  []
  (let [loaded? @(a/subscribe [:db/get-item [:home :menu-handler/loaded?]])]
       [common/surface-breadcrumbs :home/view
                                   {:crumbs [{:label :app-home}]
                                    :loading? (not loaded?)}]))

(defn- home-title
  []
  (let [loaded?   @(a/subscribe [:db/get-item [:home :menu-handler/loaded?]])
        app-title @(a/subscribe [:core/get-app-config-item :app-title])]
       [common/surface-label :home/view
                             {:label app-title
                              :loading? (not loaded?)}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [:div {:style {:display :flex :flex-wrap :wrap}}
        [elements/ghost {:border-radius :m :height :xxl :style {:width "240px"} :indent {:vertical :xs :top :xxl}}]
        [elements/ghost {:border-radius :m :height :xxl :style {:width "240px"} :indent {:vertical :xs :top :xxl}}]
        [elements/ghost {:border-radius :m :height :xxl :style {:width "240px"} :indent {:vertical :xs :top :xxl}}]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- label-group-item-content
  [{:keys [color icon icon-family label]}]
  [:div {:style {:display :flex}}
        [elements/icon {:color       color
                        :icon        icon
                        :icon-family icon-family
                        :indent      {:horizontal :xs}
                        :size        :l}]
        [elements/label {:content label
                         :indent  {}}]])

(defn- label-group-item
  [{:keys [on-click] :as group-item}]
  [elements/card {:border-radius    :m
                  :content          [label-group-item-content group-item]
                  :on-click         on-click
                  :horizontal-align :left
                  :hover-color      :highlight
                  :min-width        :s
                  :indent           {:vertical :xs}}])

(defn- label-group
  [label label-group]
  (letfn [(f [group-item-list group-item]
             (conj group-item-list [label-group-item group-item]))]
         (reduce f [:<>] label-group)))

(defn- horizontal-group
  [horizontal-weight horizontal-group]
  ; Az azonos vertical-group csoportokban és azon belül is azonos horizontal-group
  ; csoportokban felsorolt menü elemek a label tulajdonságuk szerinti kisebb
  ; csoportokban vannak felsorolva.
  (let [label-groups (group-by #(-> % :label components/content) horizontal-group)]
       (letfn [(f [label-group-list label]
                  (conj label-group-list [label-group label (get label-groups label)]))]
              (reduce f [:<>] (-> label-groups keys sort)))))

(defn- vertical-group-label
  [group-name]
  (let [group-items @(a/subscribe [:home/get-menu-group-items group-name])]
       (if (vector/nonempty? group-items)
           [elements/label {:color     :muted
                            :content   group-name
                            :font-size :l
                            :indent    {:top :xxl :vertical :xs}}])))

(defn- vertical-group
  [group-name]
  ; Az azonos vertical-group csoportokban felsorolt menü elemek a horizontal-weight
  ; tulajdonságuk szerinti kisebb csoportokban vannak felsorolva.
  [:<> [vertical-group-label group-name]
       [:div {:style {:display "flex" :flex-wrap "wrap" :grid-row-gap "12px"}}
             (let [group-items      @(a/subscribe [:home/get-menu-group-items group-name])
                   horizontal-groups (group-by :horizontal-weight group-items)]
                  (letfn [(f [horizontal-group-list horizontal-weight]
                             (conj horizontal-group-list [horizontal-group horizontal-weight (get horizontal-groups horizontal-weight)]))]
                         (reduce f [:<>] (-> horizontal-groups keys sort))))]])

(defn- menu-groups
  []
  ; A menü elemek elsődlegesen a group tulajdonságuk szerint csoportosítva
  ; vannak felsorolva a vertical-group csoportokban.
  (if-let [loaded? @(a/subscribe [:db/get-item [:home :menu-handler/loaded?]])]
          (letfn [(f [vertical-group-list group-name]
                     (conj vertical-group-list [vertical-group group-name]))]
                 (reduce f [:<>] config/GROUP-ORDER))
          [ghost-view]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  ; @param (keyword) surface-id
  [surface-id]
  [:<> [home-title]
       [breadcrumbs]
       ;[elements/horizontal-separator {:size :xxl}]
       [menu-groups]])

(defn view
  ; @param (keyword) surface-id
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
