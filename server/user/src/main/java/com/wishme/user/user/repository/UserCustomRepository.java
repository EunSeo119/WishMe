package com.wishme.user.user.repository;

import java.util.List;

public interface UserCustomRepository {

    List<String> findEmailsByUserAndMyletter();

    List<String> findEmailsByUserAndReply();
}
