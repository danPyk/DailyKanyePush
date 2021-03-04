/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.dailykanyepush.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.dailykanyepush.MainActivity
import com.example.dailykanyepush.R


// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

/**
 * Builds and delivers the notification.
 *
 * @param messageBody, notification text.
 * @param context, activity context.
 *
 *
 */

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

        //  create intent
        val contentIntent = Intent(applicationContext, MainActivity::class.java)

        // 2 create PendingIntent
        val contentPendingIntent = PendingIntent.getActivity(
                applicationContext,
                NOTIFICATION_ID,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        //add style
        val kanyeFace = BitmapFactory.decodeResource(
                applicationContext.resources,
                R.drawable.kanye_face
        )
        val bigPicStyle = NotificationCompat.BigPictureStyle()
                .bigPicture(kanyeFace)
                .bigLargeIcon(null)
        val bigTextStyle = NotificationCompat.BigTextStyle()

        // get an instance of NotificationCompat.Builder
        // Build the notification, support prev ver of andoird
        val builder = NotificationCompat.Builder(
                applicationContext,
                // use the new 'breakfast' notification channel
                applicationContext.getString(R.string.egg_notification_channel_id)
        )
                //  set title, text and icon to builder
                .setSmallIcon(R.drawable.kanye2)
                .setContentTitle(applicationContext.getString(R.string.notification_title))

                .setContentText(messageBody)
                //  set content intent
                .setContentIntent(contentPendingIntent)
                //  add style to builder
                //.setStyle(bigPicStyle)
               .setStyle(bigTextStyle)

                .setLargeIcon(kanyeFace)
                //  Step 2.3 add snooze action
                .addAction(
                        R.drawable.kanye2,
                    null,
                        null
                )
                // set priority
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
        //call notify
        // Deliver the notification/**/
        notify(NOTIFICATION_ID, builder.build())
    }




