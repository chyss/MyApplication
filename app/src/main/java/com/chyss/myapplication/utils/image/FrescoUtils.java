package com.chyss.myapplication.utils.image;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Administrator on 2017/4/17.
 */

public class FrescoUtils
{
    public static void loadImage(String imgUrl, SimpleDraweeView sv)
    {
        Uri aniImageUri = Uri.parse(imgUrl);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(aniImageUri)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        sv.setController(controller);
    }
}
