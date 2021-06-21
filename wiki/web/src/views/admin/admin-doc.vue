<template>
  <a-layout style="padding: 24px 0; background: #fff">
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <a-row :gutter="24">
        <a-col :span="8">
          <p>
            <a-space size="small">
              <a-button type="primary" @click="choose()" size="large">
                查询
              </a-button>
              <a-button type="primary" @click="add()" size="large">
                新增
              </a-button>
            </a-space>
          </p>
          <a-table
              v-if="level1.length>0"
              :columns="columns"
              :row-key="record => record.id"
              :data-source="level1"
              :loading="loading"
              :pagination="false"
              size="small"
              :defaultExpandAllRow="true"
          >
            <template #name="{ text,record }">
              {{ record.sort }} {{ text }}
            </template>
            <template v-slot:action="{ text, record }">
              <a-space size="small">
                <a-button type="primary" @click="edit(record)" size="small">
                  编辑
                </a-button>
                <a-popconfirm
                    title="删除后不可恢复，确认删除?"
                    ok-text="是"
                    cancel-text="否"
                    @confirm="handleDelete(record.id)"
                >
                  <a-button type="danger" size="small">
                    删除
                  </a-button>
                </a-popconfirm>
              </a-space>
            </template>
          </a-table>
        </a-col>
        <a-col :span="16">
          <p>
            <a-form layout="inline" :model="param">
              <a-form-item>
                <a-button type="primary" @click="handleSave()">
                  保存
                </a-button>
              </a-form-item>
            </a-form>
          </p>
          <a-form :model="doc" layout="vertical">
            <a-form-item>
              <a-input v-model:value="doc.name" placeholder="名称"/>
            </a-form-item>
            <a-form-item>
              <a-tree-select
                  v-model:value="doc.parent"
                  style="width: 100%"
                  :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                  :tree-data="treeSelectData"
                  placeholder="选择父文挡"
                  tree-default-expand-all
                  :replaceFields="{title:'name', key:'id', value: 'id'}"
              >
              </a-tree-select>
            </a-form-item>
            <a-form-item>
              <a-input v-model:value="doc.sort" placeholder="顺序"/>
            </a-form-item>
            <a-form-item>
              <div id="content"></div>
            </a-form-item>
          </a-form>
        </a-col>
      </a-row>
    </a-layout-content>
  </a-layout>

  <!--  <a-modal-->
  <!--      title="文档表单"-->
  <!--      v-model:visible="modalVisible"-->
  <!--      :confirm-loading="modalLoading"-->
  <!--      @ok="handleModalOk"-->
  <!--  >-->

  <!--  </a-modal>-->

</template>
<script lang="ts">

import {defineComponent, onMounted, ref, createVNode} from 'vue';
import axios from 'axios'
import {message} from 'ant-design-vue';
import {Tool} from '@/util/tool';
import {useRoute} from "vue-router";
import {Modal} from 'ant-design-vue';
import {ExclamationCircleOutlined} from "@ant-design/icons-vue";
import E from 'wangeditor'

export default defineComponent({
  name: 'AdminDoc',
  setup() {
    const route = useRoute();
    const docs = ref();
    const level1 = ref();
    level1.value=[];
    const loading = ref(false);
    const doc = ref();
    const modalVisible = ref(false);
    const modalLoading = ref(false);
    const editor = new E("#content")
    const treeSelectData = ref();
    editor.config.zIndex=0;
    doc.value = {
      ebookId: route.query.ebookId
    };
    treeSelectData.value = [];

    let deleteData: Array<string> = [];
    let deleteName: Array<string> = [];
    const columns = [

      {
        title: '名称',
        dataIndex: 'name',
        slots: {customRender: 'name'}
      },
      {
        title: 'Action',
        key: 'action',
        slots: {customRender: 'action'}
      }];
    /**
     * 数据查询
     **/
    const handleQuery = () => {
      loading.value = true;

      axios.get("/doc/all",
          {
            params: {
              ebookId: route.query.ebookId,
            }
          }).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          docs.value = data.content;
          console.log("原始数组：", docs.value);
          level1.value = [];
          level1.value = Tool.array2Tree(docs.value, 0);
          console.log("树形结构：", level1);
        } else {
          message.error(data.message);
        }
      });
    };
    /**
     * 内容查询
     **/
    const handleContent = () => {
      axios.get("/doc/find-content/"+doc.value.id).then((response) => {
        const data = response.data;
        if (data.success) {
          editor.txt.html(data.content);

        } else {
          message.error(data.message);
        }
      });
    };
    const setDisable = (treeSelectData: any, id: any) => {

      for (let i = 0; i < treeSelectData.length; i++) {
        const node = treeSelectData[i];
        if (node.id === id) {
          node.disabled = true;
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            for (let j = 0; j < children.length; j++) {
              setDisable(children, children[j].id);
            }
          }
        } else {
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            setDisable(children, id);
          }
        }
      }
    }
    const setDelete = (treeSelectData: any, id: any) => {

      for (let i = 0; i < treeSelectData.length; i++) {
        const node = treeSelectData[i];
        if (node.id === id) {
          deleteData.push(node.id);
          deleteName.push(node.name);
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            for (let j = 0; j < children.length; j++) {
              setDelete(children, children[j].id);
            }
          }
        } else {
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            setDelete(children, id);
          }
        }
      }
    }

    const handleSave = () => {
      modalLoading.value = true;
      doc.value.content=editor.txt.html();
      axios.post("/doc/save",
          doc.value).then((response) => {
        modalLoading.value = false;
        const data = response.data;
        if (data.success) {
          modalVisible.value = false;

          //重新加载列表
          handleQuery();
        } else {
          message.error(data.message);

        }
      });
    };

    // 新增
    const add = () => {

      modalVisible.value = true;
      doc.value = {
        ebookId: route.query.ebookId
      };

      treeSelectData.value = Tool.copy(level1.value);
      treeSelectData.value.unshift({id: 0, name: '无'});
    };

    /**
     * 编辑
     */
    const edit = (record: any) => {
      modalVisible.value = true;
      doc.value = Tool.copy(record);
      handleContent();
      treeSelectData.value = Tool.copy(level1.value);
      setDisable(treeSelectData.value, record.id);
      treeSelectData.value.unshift({id: 0, name: '无'});
    };
    //删除
    //Long类型对应的前段类型是number
    const handleDelete = (id: string) => {
      deleteData = [];
      deleteName = [];
      setDelete(level1.value, id);
      Modal.confirm({
        title: '危！！！',
        icon: createVNode(ExclamationCircleOutlined),
        content: '真的要删除【' + deleteData.join(",") + '】这一些文件吗?',
        onOk() {
          axios.delete("/doc/delete/" +
              deleteData.join(",")).then((response) => {

            const data = response.data;
            if (data.success) {

              //重新加载列表
              handleQuery();
            }
          });
        }
      });
    };
    const name = ref<string>('');
    const choose = (params: any) => {
      loading.value = true;
      handleQuery();
    };
    onMounted(function () {
      handleQuery();
      editor.create();
    });

    return {
      level1,
      columns,
      loading,
      treeSelectData,
      edit,
      add,
      name,
      handleDelete,
      choose,
      editor,
      doc,
      modalVisible,
      modalLoading,
      handleSave,
    }
  }
});
</script>
<style>
img {
  vertical-align: middle;
  border-style: none;
  width: 50px;
}
</style>
