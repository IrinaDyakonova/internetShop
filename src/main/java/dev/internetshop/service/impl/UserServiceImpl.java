package dev.internetshop.service.impl;

import dev.internetshop.dao.UserDao;
import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Inject;
import dev.internetshop.lib.Service;
import dev.internetshop.model.User;
import dev.internetshop.service.UserService;
import dev.internetshop.util.HashUtil;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public User create(User user)
            throws DataProcessingException {
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
        return userDao.create(user);
    }

    @Override
    public User get(Long id)
            throws DataProcessingException {
        return userDao.get(id).orElseThrow();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }

}
