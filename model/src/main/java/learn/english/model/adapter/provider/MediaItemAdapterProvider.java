package learn.english.model.adapter.provider;

import learn.english.model.entity.MediaItem;

/**
 * Created by yaroslav on 9/18/14.
 */
public interface MediaItemAdapterProvider {
    public MediaItem unmarshal(MediaItem v);

    MediaItem marshal(MediaItem v);
}
