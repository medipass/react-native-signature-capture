require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-signature-capture"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.author       = package["author"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.platform     = :ios, "11.0"
  s.source       = { :git => "https://github.com/RepairShopr/react-native-signature-capture", :tag => "#{s.version}" }
  s.source_files  = "ios/*.{h,m,mm}"

  # React Native Core dependency
  s.dependency "React-Core"

  # New Architecture support
  if ENV['RCT_NEW_ARCH_ENABLED'] == '1' then
    s.compiler_flags = '-DRCT_NEW_ARCH_ENABLED=1'
    s.pod_target_xcconfig = {
        'HEADER_SEARCH_PATHS' => '"$(PODS_ROOT)/boost" "$(PODS_ROOT)/Headers/Private/React-Core"',
        'CLANG_CXX_LANGUAGE_STANDARD' => 'c++17'
    }

    s.dependency "React-Codegen"
    s.dependency "RCT-Folly"
    s.dependency "RCTRequired"
    s.dependency "RCTTypeSafety"
    s.dependency "ReactCommon/turbomodule/core"
  end

  # This allows the library to work with the `install_modules_dependencies`
  # helper provided by React Native 0.71+
  if respond_to?(:install_modules_dependencies)
    install_modules_dependencies(s)
  end
end
