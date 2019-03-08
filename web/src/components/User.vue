<template>
  <a-table :columns="columns"
           :rowKey="record => record.login.uuid"
           :dataSource="data"
           :pagination="pagination"
           :loading="loading"
           @change="handleTableChange"
  >

  </a-table>
</template>

<script>
  const columns = [{
    title: 'ID',
    dataIndex: 'id',
    sorter: true,
    width: '20%'
  }, {
    title: 'Name',
    dataIndex: 'name',
    sorter: true,
    scopedSlots: { customRender: 'name' }
  }];

  export default {
    name: "User",
    mounted() {
      this.fetch();
    },
    data() {
      return {
        data: [],
        pagination: {},
        loading: false,
        columns
      }
    },
    methods: {
      handleTableChange (pagination, filters, sorter) {
        console.log(pagination);
        const pager = { ...this.pagination };
        pager.current = pagination.current;
        this.pagination = pager;
        this.fetch({
          pageSize: pagination.pageSize,
          pageNum: pagination.current,
          sortField: sorter.field,
          sortOrder: sorter.order,
          ...filters
        });
      },
      fetch (params = {}) {
        console.log('params:', params);
        this.loading = true;

        this.axios.get('/api/user', this.qs.stringify(params))
          .then((response) => {
            const pagination = { ...this.pagination };
            pagination.total = response.total;
            this.loading = false;
            this.data = response.results;
            this.pagination = pagination;
          })
          .catch((error) => {
            console.log(error);
            this.$router.push('/Login');
          });
      }
    }
  }
</script>

<style scoped>

</style>
