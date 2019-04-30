Java JDK: 8
Android SDK: API 28
AVD: Google Pixel
Minimum SDK: API 14

1. Create emulator using AVD manager
2. Open command-line session and run: emulator @DEVICE_NAME
3. In another session, run: adb install app-debug.apk
4. Open the app
5. Wait at least 5 seconds before pressing the load images button (for async task to download images from network)

Notes:
- To give a rating of 0, click and drag stars to the left
- To reset filter back to 0 stars, do loading/clearing of images