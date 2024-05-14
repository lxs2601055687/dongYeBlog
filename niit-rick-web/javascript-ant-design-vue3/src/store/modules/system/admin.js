import { defineStore } from 'pinia';

export const useAdminStore = defineStore({
    id: 'admin',
    state: () => ({
        isAdminLoggedIn: false,
    }),
    actions: {
        adminLoginPrepare() {
            this.isAdminLoggedIn = "prepare";
        },
        adminLogout() {
            this.isAdminLoggedIn = "false";
        },
        adminLogin() {
            this.isAdminLoggedIn = "true";
        },
        getAdminLoginStatus() {
            return this.isAdminLoggedIn;
        }
    },
    getters: {
        isLoggedIn() {
            return this.isAdminLoggedIn;
        },
    },
});
