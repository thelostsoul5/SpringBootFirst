package xyz.thelostsoul.base.split.inter;

import java.util.List;

public interface ISplitFieldParser {
    /**
     * ����ֱ��ֶΣ��õ��ֱ�����
     * @param var1 �ֱ��ֶ�ֵ
     * @return �ֱ�����
     * @throws Exception ȡֵ����
     */
    String convert(Object var1) throws Exception;

    /**
     * ����ѯ���������ڷֱ�������ʱ�򣬷���Ĭ�ϵ������б�
     * @return �����б�
     */
    List<String> all();
}
