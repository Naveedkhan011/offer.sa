package preference

import platform.Foundation.NSUserDefaults
import platform.darwin.NSObject

actual typealias KMMContext = NSObject

object IOSContext {
    val preferences: KMMContext = NSUserDefaults.standardUserDefaults
}