# Upgrading to React Native New Architecture (Fabric)

This guide outlines the steps taken to make `react-native-signature-capture` compatible with the React Native New Architecture.

## 1. Codegen Specification

The New Architecture relies on strictly typed specifications to generate the "glue code" (C++) between JavaScript and Native.

- Created `src/specs/RSSignatureViewNativeComponent.ts`.
- Defined `NativeProps` including all previous properties with `WithDefault` or `ColorValue` types.
- Defined `NativeCommands` for `saveImage` and `resetImage`.
- Added `codegenConfig` to `package.json`.

## 2. JavaScript Layer Changes

The component was updated to use the generated spec.

- Replaced `requireNativeComponent` and `UIManager.dispatchViewManagerCommand` with the generated `RSSignatureView` and `Commands`.
- The `SignatureCapture.js` now acts as a wrapper that maps the legacy API to the new Codegen-based component.

## 3. Android Implementation

Android support involves a "dual-arch" setup using source sets.

### Build Configuration
- Updated `android/build.gradle` to include the `com.facebook.react` plugin when `newArchEnabled=true`.
- Added `sourceSets` to toggle between `src/oldarch` and `src/newarch`.
- Modernized `compileSdkVersion` and `minSdkVersion`.

### Code Changes
- **Old Arch (`src/oldarch`)**: Contains the legacy `RSSignatureCapturePackage` and manager logic.
- **New Arch (`src/newarch`)**: Implements `RSSignatureViewManager` using the generated `RSSignatureViewManagerInterface` and `RSSignatureViewManagerDelegate`.
- **Common Logic**: The core view logic remains in `src/main/java`, but the Manager now uses `receiveCommand` via the Delegate in New Arch.

## 4. iOS Implementation

iOS uses the C++ generated headers from Codegen.

- The `RSSignatureViewManager.m` was updated to support the New Architecture by checking for `RCT_NEW_ARCH_ENABLED`.
- Managed command routing through the generated `RSSignatureViewComponentDescriptor`.
- Updated `.podspec` to include the necessary dependencies for Codegen and Fabric.

## How to use in your project

To enable the New Architecture in your app:

### Android
In your `android/gradle.properties`, set:
```properties
newArchEnabled=true
```

### iOS
Run pod install with the environment variable:
```bash
RCT_NEW_ARCH_ENABLED=1 bundle exec pod install
```

## Troubleshooting

If you encounter issues with `View.propTypes` errors, ensure you are using the updated `SignatureCapture.js` which has removed the dependency on `prop-types` and `View.propTypes` in favor of the TypeScript spec.