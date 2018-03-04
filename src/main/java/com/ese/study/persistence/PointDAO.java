package com.ese.study.persistence;

/**
 * PointDAO
 * 
 * 메시지 전송이나 개봉에 따라서 달라지는 사용자의 포인트를 처리
 * 
 * MessageDAO에 추가적인 메소드를 선언해서 사용하는 것도 가능하지만
 * 포인트와 메시지는 다른 도메인이므로 분리
 * @author Administrator
 *
 */

public interface PointDAO {

	public void updatePoint(String uid, int point) throws Exception;
	
}
