package xyz.thelostsoul.base.parser.inter;

import java.util.List;

public interface IRouteFieldParser {
    /**
     * ����ֱ��ֶΣ��õ��ֱ�����
     * @param var1 �ֱ��ֶ�ֵ
     * @return �ֱ�����
     * @throws Exception ȡֵ����
     */
    String convert(Object var1) throws Exception;

    /**
     * ����ѯ���������ڷֱ�������ʱ�򣬷���Ĭ�ϵ������б�
     * ��Ϊ�ܶ�����²���ͨ��������ͼ��ȫ���ֱ�������������·ֱ�������
     * ����ͨ�����ַ����涨��ѯ�ķ�Χ
     * @return �����б�
     */
    List<String> all();
}
