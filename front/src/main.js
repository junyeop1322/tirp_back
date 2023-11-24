import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import { library } from "@fortawesome/fontawesome-svg-core";
import Vue3Autocounter from "vue3-autocounter";
/* import font awesome icon component */
import { faArrowUpRightFromSquare } from "@fortawesome/free-solid-svg-icons";
import { faMap } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

import App from "./App.vue";
import router from "./router";

import "vuetify/styles";
import { createVuetify } from "vuetify";
import { aliases, mdi } from "vuetify/iconsets/mdi";
import "@mdi/font/css/materialdesignicons.css";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import * as labsComponents from "vuetify/labs/components";
const app = createApp(App);
library.add(faArrowUpRightFromSquare);
library.add(faMap);
console.log("aliases", aliases);
console.log("mdi", mdi);

const vuetify = createVuetify({
  components,
  directives,
  labsComponents,
  icons: {
    defaultSet: "mdi",
    aliases,
    sets: {
      mdi,
    },
  },
});

app.use(createPinia().use(piniaPluginPersistedstate));
app.use(router);
app.use(vuetify);
app.component("font-awesome-icon", FontAwesomeIcon);
app.component("vue3-autocounter", Vue3Autocounter);
app.mount("#app");
