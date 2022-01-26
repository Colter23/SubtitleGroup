<template>
  <separator :text="data.organization"/>
  <q-item
      @click="$router.push(data.homeLink)"
      :active="$route.path===data.homeLink"
      active-class="active-link"
      clickable v-ripple exact>
    <q-item-section avatar>
      <q-avatar rounded>
        <img :src="data.avatar">
      </q-avatar>
    </q-item-section>
    <q-item-section>{{data.homeText}}</q-item-section>
  </q-item>
  <q-item
      v-for="u in data.group"
      @click="$router.push(u.link)"
      :active="$route.path.includes(u.link)"
      active-class="active-link"
      clickable v-ripple exact>
    <q-item-section avatar>
      <q-avatar rounded>
        <img :src="u.avatar">
      </q-avatar>
    </q-item-section>
    <q-item-section>{{u.name}}</q-item-section>
  </q-item>
</template>

<script>
import Separator from "components/Separator";
export default {
  name: 'GroupLink',
  components: {Separator},
  props: {
    data: Object
  }
}
</script>
