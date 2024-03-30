<script>
export default {
  name: 'app-navbar'
}
</script>

<template>
  <v-layout class="navbar">
    <v-app-bar elevation="7" color="primary">
      <v-toolbar-title class="title">
        <router-link to="/" class="nav-link">
          <span class="nav-item">
            SQL Trainer
          </span>
        </router-link>
      </v-toolbar-title>
      <div class="nav-items">
        <v-toolbar-items>
          <v-item-group class="nav-item-group">
            <v-item>
              <router-link to="/users" style="text-decoration: none">
                <span class="nav-item">
                  Пользователи
                </span>
              </router-link>
            </v-item>
          </v-item-group>
        </v-toolbar-items>
      </div>
      <v-spacer/>
      <v-btn variant="elevated">Выйти</v-btn>
    </v-app-bar>
  </v-layout>
</template>

<style scoped>
.navbar {
  margin-bottom: 80px;
}

.title {
  max-width: max-content;
}

.nav-items {
  margin-right: 15px;
  margin-left: 15px;
}

.nav-item-group {
  display: flex;
  flex-direction: row;
}

.nav-item {
  height: 100%;
  vertical-align: middle;
  margin-left: 15px;

  font-weight: normal;
  font-size: 1.2rem;
  color: white;
}

.nav-link {
  text-decoration: none;
}
</style>