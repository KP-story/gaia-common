package com.delight.gaia.core.messaging.pubsub.local;

import com.delight.gaia.base.message.GaiaMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LocalEvent {
    private String topic;
    private GaiaMessage event;
}
