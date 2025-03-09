package com.bokbazzar.service;

import com.bokbazzar.dto.UserDto;
import com.bokbazzar.entity.RoleEntity;
import com.bokbazzar.entity.UserEntity;
import com.bokbazzar.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class UsersService implements UserDetailsService {
	
	UsersRepository usersRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
	{
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = usersRepository.findByEmail(username);
		
		if(userEntity == null) throw new UsernameNotFoundException(username);

		ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		Collection<RoleEntity> roles = userEntity.getRoles();
		roles.forEach(role -> {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			role.getAuthorities().forEach(authority -> {
				grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
			});
		});

		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),
				true, true, true, true, grantedAuthorities);
	}

	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = usersRepository.findByEmail(email);

		if(userEntity == null) throw new UsernameNotFoundException(email);

		return new ModelMapper().map(userEntity, UserDto.class);
	}

}
