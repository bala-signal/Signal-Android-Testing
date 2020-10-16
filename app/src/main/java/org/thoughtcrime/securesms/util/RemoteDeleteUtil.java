package org.thoughtcrime.securesms.util;

import androidx.annotation.NonNull;

import com.annimon.stream.Stream;

import org.thoughtcrime.securesms.database.model.MessageRecord;
import org.thoughtcrime.securesms.recipients.Recipient;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public final class RemoteDeleteUtil {

  private static final long RECEIVE_THRESHOLD = TimeUnit.DAYS.toMillis(1);
  private static final long SEND_THRESHOLD    = TimeUnit.HOURS.toMillis(3);

  private RemoteDeleteUtil() {}

  public static boolean isValidReceive(@NonNull MessageRecord targetMessage, @NonNull Recipient deleteSender, long deleteServerTimestamp) {
    boolean isValidIncomingOutgoing = (deleteSender.isLocalNumber() && targetMessage.isOutgoing()) ||
                                      (!deleteSender.isLocalNumber() && !targetMessage.isOutgoing());

    boolean isValidSender = targetMessage.getIndividualRecipient().equals(deleteSender) ||
                            deleteSender.isLocalNumber() && targetMessage.isOutgoing();

    long messageTimestamp = deleteSender.isLocalNumber() && targetMessage.isOutgoing() ? targetMessage.getDateSent()
                                                                                       : targetMessage.getServerTimestamp();

    return isValidIncomingOutgoing &&
           isValidSender           &&
           (deleteServerTimestamp - messageTimestamp) < RECEIVE_THRESHOLD;
  }

  public static boolean isValidSend(@NonNull Collection<MessageRecord> targetMessages, long currentTime) {
    // TODO [greyson] [remote-delete] Update with server timestamp when available for outgoing messages
    return Stream.of(targetMessages).allMatch(message -> isValidSend(message, currentTime));
  }

  private static boolean isValidSend(MessageRecord message, long currentTime) {
    return message.isOutgoing()                                                          &&
           message.isPush()                                                              &&
           (!message.getRecipient().isGroup() || message.getRecipient().isActiveGroup()) &&
           !message.getRecipient().isLocalNumber()                                       &&
           !message.isRemoteDelete()                                                     &&
           (currentTime - message.getDateSent()) < SEND_THRESHOLD;
  }
}
