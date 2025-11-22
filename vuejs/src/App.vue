<template>
  <div id="app">
    <div class="main-search-box">
      <label for="search">Search:</label>
      <input
        type="text"
        name="search"
        id="searchbox"
        v-model="search"
        placeholder="Type to search..."
      />
    </div>

    <table border="1">
      <thead>
        <tr>
          <th>No</th>
          <th>Guest Name</th>
          <th>Booking Date</th>
        </tr>
      </thead>
      <tbody>
        <!-- If there's a message show it in a full-width row -->
        <tr v-if="message" class="message">
          <td colspan="3">{{ message }}</td>
        </tr>

        <!-- Otherwise show the rows -->
        <template v-else>
          <tr v-for="(item, index) in body" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ item.guestName }}</td>
            <td>{{ item.bookingDate }}</td>
          </tr>

          <!-- Show a "no data" row if body is empty and there is no message -->
          <tr v-if="!body.length && !message">
            <td colspan="3">No data.</td>
          </tr>
        </template>
      </tbody>
    </table>

    <div class="main-button">
      <div>
        <button @click="prevPage" :disabled="page === 0">Prev</button>
        <button @click="nextPage">Next</button>
      </div>
      <p>Page: {{ page1 }}-{{ totalPage }}</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      body: [],
      page: 0,
      page1: 1,
      size: 5,
      message: '',
      search: '',
      searchTimeout: null, // for debounce
      totalPage: ''
    };
  },

  mounted() {
    this.loadData();
  },

  watch: {
    // When page changes, decide which fetch to run based on whether search is empty
    page() {
      if (this.search === '') {
        this.loadData();
      } else {
        this.searchData();
      }
    },

    // Debounced search watcher: resets page to 0 and calls the proper API
    search(newVal) {
      // clear previous debounce
      if (this.searchTimeout) {
        clearTimeout(this.searchTimeout);
      }

      // small debounce so we don't request on every keystroke
      this.searchTimeout = setTimeout(() => {
        // when starting a new search, reset to first page
        this.page = 0;

        if (newVal === '') {
          this.loadData();
        } else {
          this.searchData();
        }
      }, 300); // 300ms debounce
    },
  },

  methods: {
    loadData() {
      fetch(`http://localhost:8080/api/todo?page=${this.page}&size=${this.size}`)
        .then((response) => response.json())
        .then((data) => {
          if (data.message) {
            this.message = data.message;
            this.body = []; // clear rows when message present
            return;
          }

          if (data.content) {
            this.body = data.content;
            this.totalPage = data.totalPages;
            this.message = '';
          } else {
            // fallback: empty content
            this.body = [];
            this.message = '';
          }
        })
        .catch((error) => {
          console.error('Error fetching data:', error);
          this.message = 'Error fetching data';
          this.body = [];
        });
    },

    searchData() {
      // include size if your search endpoint supports it, adjust as needed
      fetch(
        `http://localhost:8080/api/todo1?keyword=${encodeURIComponent(
          this.search
        )}&page=${this.page}&size=3`
      )
        .then((response) => response.json())
        .then((data) => {
          if (data.message) {
            this.message = data.message;
            this.body = [];
            return;
          }

          if (data.content) {
            this.body = data.content;
            this.totalPage = data.totalPages;
            this.message = '';
          } else {
            this.body = [];
            this.message = '';
          }
        })
        .catch((error) => {
          console.error('Error searching data:', error);
          this.message = 'Error searching data';
          this.body = [];
        });
    },

    prevPage() {
      if (this.page > 0) {
        this.page -= 1;
        this.page1 -= 1;
      }
    },

    nextPage() {
      // If you have total pages you can check and disable when last page
      this.page += 1;
      this.page1 += 1;
    },
  },
};
</script>

<style>
#app {
  display: flex;
  flex-direction: column;
}
table {
  border-collapse: collapse;
  width: 100%;
}
tr th,
tr td {
  padding: 10px;
}
.main-button {
  margin-top: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
button{
  cursor:pointer;
}
button:disabled{
  cursor: not-allowed;
}
.message {
  text-align: center;
}
.main-search-box {
  margin-bottom: 10px;
}
.main-search-box input {
  width: 100%;
  padding: 8px 5px;
  font-size: 16px;
}
</style>
