package service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import domain.Member;
import domain.dto.RegisterRequest;
import exception.DuplicateMemberException;
import model.MemberDao;

@Component
public class MemberRegisterService {
	@Autowired
	private MemberDao memberDao;
	
	
	public MemberRegisterService(MemberDao memberDao) {
		// TODO Auto-generated constructor stub
		this.memberDao=memberDao;
	}
	
	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		
		if (member != null) {
			throw new DuplicateMemberException("dup email: "+req.getEmail());
		}
		Member newMember = new Member(req.getEmail(),req.getPassword(),req.getName(),LocalDateTime.now());
		memberDao.insert(newMember);
		return newMember.getId();
	}
}
