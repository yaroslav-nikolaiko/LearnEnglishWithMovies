package learn.english.model.adapter;

import learn.english.model.adapter.provider.MediaItemAdapterProvider;
import learn.english.model.entity.MediaItem;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by yaroslav on 9/18/14.
 */
public class MediaItemAdapter extends XmlAdapter<MediaItem, MediaItem> {
    @EJB
    MediaItemAdapterProvider provider;

    @Override
    public MediaItem unmarshal(MediaItem v) throws Exception {
        if(provider != null)
            return provider.unmarshal(v);
        else
            return v;
    }

    @Override
    public MediaItem marshal(MediaItem v) throws Exception {
        if(provider != null)
            return provider.marshal(v);
        else
            return v;
    }
}
