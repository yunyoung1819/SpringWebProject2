package com.ese.study.domain;

public class Criteria {

	private int page;  		//������
	private int perPageNum;	//ȭ��� ������ ����
	
	public Criteria(){		//������, �� �ʱ�ȭ
		this.page = 1;
		this.perPageNum = 10;
	}
	
	public void setPage(int page){  //������ ���� ����
		
		if(page <= 0){		//page���� 0�̰ų� 0���� ���� �� 1�������� ����
			this.page = 1;
			return;
		}
		
		this.page = page;
	}
	
	public void setPerPageNum(int perPageNum){	//�������� ������ ����
		
		if(perPageNum <= 0 || perPageNum > 100){	//�������� ������ 0���� �����̰ų� �������� ������ 100�� �̻��� ��
			this.perPageNum = 10;
			return;
		}
		
		this.perPageNum = perPageNum;
	}
	
	public int getPage(){
		return page;
	}
	
	//method for myBatis SQL Mapper
	public int getPageStart(){	//limit 20, 10 �������� ���� ��ġ�� ������ �� �����
		
		return (this.page -1) * perPageNum;  //���� �����͹�ȣ = (������ ��ȣ -1) * �������� �������� ����
	}
	
	//method for myBatis SQL Mapper
	public int getPerPageNum(){	//�� �������� �������� ����
		
		return this.perPageNum;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}
}
