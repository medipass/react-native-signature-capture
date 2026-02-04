import type {ViewProps} from 'react-native/Libraries/Renderer/shims/ReactNativeTypes';
import type {
  DirectEventHandler,
  Int32,
  WithDefault,
} from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';
import codegenNativeCommands from 'react-native/Libraries/Utilities/codegenNativeCommands';
import * as React from 'react';
import {HostComponent, ColorValue} from 'react-native';

export type OnChangeEvent = Readonly<{
  pathName?: string;
  encoded?: string;
  dragged?: boolean;
}>;

export interface NativeProps extends ViewProps {
  rotateClockwise?: WithDefault<boolean, false>;
  square?: WithDefault<boolean, false>;
  saveImageFileInExtStorage?: WithDefault<boolean, false>;
  viewMode?: WithDefault<string, 'portrait'>;
  showBorder?: WithDefault<boolean, true>;
  showNativeButtons?: WithDefault<boolean, true>;
  showTitleLabel?: WithDefault<boolean, true>;
  maxSize?: WithDefault<Int32, 500>;
  minStrokeWidth?: WithDefault<Int32, 0>;
  maxStrokeWidth?: WithDefault<Int32, 0>;
  strokeColor?: ColorValue;
  backgroundColor?: ColorValue;

  // Events
  onChange?: DirectEventHandler<OnChangeEvent>;
}

type ComponentType = HostComponent<NativeProps>;

interface NativeCommands {
  saveImage: (viewRef: React.ElementRef<ComponentType>) => void;
  resetImage: (viewRef: React.ElementRef<ComponentType>) => void;
}

export const Commands: NativeCommands = codegenNativeCommands<NativeCommands>({
  supportedCommands: ['saveImage', 'resetImage'],
});

export default codegenNativeComponent<NativeProps>('RSSignatureView') as ComponentType;
