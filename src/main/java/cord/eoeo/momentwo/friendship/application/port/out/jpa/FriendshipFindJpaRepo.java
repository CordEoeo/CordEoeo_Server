package cord.eoeo.momentwo.friendship.application.port.out.jpa;

import cord.eoeo.momentwo.friendship.application.port.out.FriendshipGenericJpaRepo;
import cord.eoeo.momentwo.friendship.domain.Friendship;
import cord.eoeo.momentwo.user.domain.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendshipFindJpaRepo extends FriendshipGenericJpaRepo {
    @Query("SELECT f FROM Friendship f WHERE f.fromUser.id = :toUserId AND" +
            " f.toUser.id = :fromUserId AND f.accept = :accept")
    Optional<Friendship> findByToUserAndFromUserAndAccept(long toUserId, long fromUserId, boolean accept);

    @Query("SELECT f FROM Friendship f WHERE f.fromUser = :toUser AND f.toUser = :fromUser")
    Friendship findByToUserAndFromUser(User toUser, User fromUser);

    @Query("SELECT f1 FROM Friendship f1 JOIN Friendship f2 ON f1.fromUser = f2.toUser " +
            "AND f1.toUser = f2.fromUser" +
            " WHERE f1.fromUser = :fromUser AND f1.toUser = :toUser AND f1.accept = true AND f2.accept = false")
    Optional<Friendship> findBySelfJoin(User fromUser, User toUser);

    @Query("SELECT f2 FROM Friendship f1 JOIN Friendship f2 ON f1.fromUser = f2.toUser AND f1.toUser = f2.fromUser " +
            "WHERE f2.toUser = :owner AND f1.accept = true AND f2.accept = true")
    List<Friendship> findAllFriendsByUser(User owner);

    @Query("SELECT f1 FROM Friendship f1 JOIN Friendship f2 ON f1.fromUser = f2.toUser AND f1.toUser = f2.fromUser " +
            "WHERE f1.fromUser = :owner AND f1.accept = true AND f2.accept = false")
    List<Friendship> findSendFriendsByUser(User owner);

    @Query("SELECT f1 FROM Friendship f1 JOIN Friendship f2 ON f1.fromUser = f2.toUser AND f1.toUser = f2.fromUser " +
            "WHERE f1.toUser = :owner AND f1.accept = true AND f2.accept = false")
    List<Friendship> findReceiveFriendsByUser(User owner);
}
