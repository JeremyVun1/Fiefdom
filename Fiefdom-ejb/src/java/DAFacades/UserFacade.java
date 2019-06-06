package DAFacades;

import dto.EmailDTO;
import dto.RegisterUserDTO;
import dto.UserinfoBasicDTO;
import dto.UserinfoFull_pwDTO;
import email.EmailingBeanRemote;
import entity.Userinfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class UserFacade {

	@PersistenceContext(unitName = "test-ejbPU")
	private EntityManager em;
	
	@EJB
	EmailingBeanRemote emailingBean;

	public Userinfo find(String username) {
		if (username == null)
			return null;
		
		try {
			Query q = em.createNamedQuery("Userinfo.findByUsername").setParameter("username", username);
			return (Userinfo) q.getSingleResult();
		} catch (Exception ex) {
			return null;
		}
	}

	public Userinfo find(int UId) {
		return em.find(Userinfo.class, UId);
	}

	public boolean hasUser(String username) {
		return (find(username) != null);
	}

	private void create(Userinfo user) {
		if (user == null)
			return;
		
		em.persist(user);
	}

	public boolean createUser(RegisterUserDTO regDTO) {
		//check if user already exists
		if (find(regDTO.getUsername()) != null) {
			return false;
		}
		
		Userinfo u = new Userinfo(regDTO.username, regDTO.password, "USER", regDTO.getEmail());
		u.setLoggedin(false);

		create(u);
		return true;
	}

	private void remove(Userinfo u) {
		if (u == null)
			return;
		
		em.remove(em.merge(u));
	}

	public boolean removeUser(Userinfo u) {
		if (u == null)
			return false;

		em.remove(u);
		return true;
	}

	public boolean suspendUser(String username) {
		if (username == null)
			return false;
		
		try {
			Userinfo u = find(username);

			if (u == null) {
				return false;
			}

			u.setRolegroup("SUSPENDED");
			em.merge(u);

			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean unsuspenUser(String username) {
		if (username == null)
			return false;
		
		try {
			Userinfo u = find(username);

			if (u == null) {
				return false;
			}

			u.setRolegroup("USER");
			em.merge(u);

			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean updateUser(UserinfoFull_pwDTO u) {
		if (u == null)
			return false;
		
		Userinfo curr = find(u.getId());
		if (curr == null) {
			return false;
		}

		try {
			curr.setUsername(u.getUsername());
			curr.setEmail(u.getEmail());
			curr.setRolegroup(u.getRolegroup());

			if (u.getPassword() != null) {
				curr.setPassword(u.getPassword());
			}

			em.merge(curr);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean usernameExists(String username) {
		return (find(username) != null);
	}

	public boolean isLoggedIn(String username) {
		Userinfo u = find(username);

		if (u == null) {
			return false;
		} else {
			return u.getLoggedin();
		}
	}

	public boolean tryRecordLogin(String username) {
		Userinfo u = find(username);

		if (u == null || u.getLoggedin()) {
			return false;
		} else {
			u.setLoggedin(true);
			em.persist(u);
			return true;
		}
	}

	public boolean tryRecordLogout(String username) {
		Userinfo u = find(username);

		if (u == null || !u.getLoggedin()) {
			return false;
		} else {
			u.setLoggedin(false);
			em.persist(u);
			return true;
		}
	}

	public boolean changePassword(String username, String newPass) {
		if (username == null || newPass == null) {
			return false;
		}

		Userinfo u = find(username);
		if (u == null)
			return false;

		u.setPassword(newPass);
		u = em.merge(u);
		
		if (u == null)
			return false;
		else {
			String subject = "Password Changed";
			String body = "Your password was recently changed on " + new Date().toString();
			EmailDTO email = new EmailDTO(u.getEmail(), subject, body);
			
			emailingBean.sendEmail(email);
				
			return true;
		}
	}

	//hashed password should never leave the local facade for better security isolation.
	//business tier should not have to get the hased password
	public boolean verify(String username, String hashPass) {
		if (username == null || hashPass == null) {
			return false;
		}

		Userinfo u = find(username);

		if (u == null) {
			return false;
		}

		return (u.getPassword().equals(hashPass));
	}
	
	public String getUserEmail(String username) {
		Userinfo u = find(username);
		if (u == null)
			return null;
		
		return u.getEmail();
	}

	public ArrayList<UserinfoBasicDTO> fetchUsersBasic() {
		ArrayList<UserinfoBasicDTO> result = new ArrayList<UserinfoBasicDTO>();

		Query q = em.createNamedQuery("Userinfo.findAll");
		List<Userinfo> qResult = q.getResultList();

		for (Userinfo u : qResult) {
			if (u.getUsername().equals("admin")) {
				continue;
			}

			result.add(DAO2BaseDTO(u));
		}

		return result;
	}

	public ArrayList<UserinfoBasicDTO> fetchUsersBasic(boolean fetchSuspended) {
		ArrayList<UserinfoBasicDTO> result = new ArrayList<UserinfoBasicDTO>();

		Query q = em.createNamedQuery("Userinfo.findAll");
		List<Userinfo> qResult = q.getResultList();

		for (Userinfo u : qResult) {
			if (u.getUsername().equals("admin")
					|| fetchSuspended && !u.getRolegroup().equals("SUSPENDED")
					|| !fetchSuspended && u.getRolegroup().equals("SUSPENDED")) {
				continue;
			}

			result.add(DAO2BaseDTO(u));
		}

		return result;
	}
	
	public UserinfoBasicDTO fetchUserBasic(String username) {
		return DAO2BaseDTO(find(username));
	}

	public Userinfo fetchUserByUsername(String username) {
		return find(username);
	}

	public Userinfo fetchUserById(int id) {
		return find(id);
	}

	private UserinfoBasicDTO DAO2BaseDTO(Userinfo u) {
		if (u == null)
			return null;
		
		return new UserinfoBasicDTO(u.getId(), u.getUsername());
	}
}
