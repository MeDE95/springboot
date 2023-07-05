package org.zerock.boardex.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

//회원 관리
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity {
    @Id
    private String mid;  //멤버 ID
    private String mpw;  //멤버 Password
    private String email;  //이메일
    private boolean del; 
    private boolean social;  //소셜 로그인
    
    //ElementCollection(): 값 타입 컬렉션을 매핑할 때 사용, 해당 필드가 컬렉션 객체임을 JPA에게 알려줌.
    // 1:N 관계설정(PK-FK)
    //각 회원들은 USER(사용자), ADMIN(관리자) 권한을 가질 수 있음.
    @ElementCollection(fetch = FetchType.LAZY)
    //편리하고 명확하게 객체 생성하도록 도와줌(필드 이름을 명시적으로 넣을 수 있음)
    //EX)member=member.builder().name("홍길동").nickname("쾌도").build();
    @Builder.Default
    private Set<MemberRole> roleSet=new HashSet<>();
    public void changePassword(String mpw) {
        this.mpw=mpw;
    }
    public void changeEmail(String email) {
        this.email=email;
    }
    public void changeDel(boolean del) {
        this.del=del;
    }
    //회원에게 권한 추가 메서드
    public void addRole(MemberRole memberRole) {
        this.roleSet.add(memberRole);
    }
    //권한 삭제
    public void clearRoles() {
        this.roleSet.clear();
    }
    public void changeSocial(boolean social) {
        this.social=social;
    }
}
