(function(selector) {
  const fullXPath = function(el) {
    const stack = [];
    while (el.parentNode != null) {
      let sibCount = 0;
      let sibIndex = 0;
      for (var i = 0; i < el.parentNode.childNodes.length; i++) {
        const sib = el.parentNode.childNodes[i];
        if (sib.nodeName === el.nodeName) {
          if (sib === el) {
            sibIndex = sibCount;
          }
          sibCount++;
        }
      }
      if (sibCount > 1) {
        stack.unshift(el.nodeName.toLowerCase() + '[' + (sibIndex + 1) + ']');
      } else {
        stack.unshift(el.nodeName.toLowerCase());
      }
      el = el.parentNode;
    }

    return "/" + stack.join("/");
  };

  const cssPath = function(el) {
    if (!(el instanceof Element)) {
      return;
    }

    const paths = [];
    while (el.nodeType === Node.ELEMENT_NODE) {
      let nodeName = el.nodeName.toLowerCase();
      if (el.id && document.querySelectorAll("#" + CSS.escape(el.id)).length === 1) {
        paths.unshift(`${nodeName}#${CSS.escape(el.id)}`);
        break;
      }

      let sib = el, nth = 1;
      while (sib = sib.previousElementSibling) {
        if (sib.nodeName.toLowerCase() === nodeName) {
          nth++;
        }
      }
      paths.unshift(nth !== 1 ? `${nodeName}:nth-of-type(${nth})` : nodeName);
      el = el.parentNode;
    }
    return paths.join(" > ");
  };

  const texts = function(el) {
    if (! el.hasChildNodes()) {
      return null;
    }

    const ret = [];
    el.childNodes.forEach(childEl => {
      if (childEl.nodeName.toLowerCase() === "#text") {
        if (childEl.nodeValue && childEl.nodeValue.trim().length > 0) {
          ret.push(childEl.nodeValue);
        }
      }
    });

    return ret;
  };

  const sliceMap = function(obj, allowedKeys) {
    const ret = {};

    allowedKeys.forEach((key) => {
      ret[key] = obj[key];
    });

    return ret;
  }

  const descendantTextsWithBGImage = function(el) {
    const isBackgroundImageNone = function(style) {
      let backgroundImage = style.backgroundImage;
      if (backgroundImage == null || "" === backgroundImage) {
        // backgroudImage's default is "none"
        return true;
      }

      backgroundImage = backgroundImage.toLowerCase();
      return ("none" === backgroundImage);
    }

    const isBackgroundColorTransparent = function(style) {
      let backgroundColor = style.backgroundColor;
      if (backgroundColor == null || "" === backgroundColor) {
        // backgroundColor's default is "transparent"
        return true;
      }

      backgroundColor = backgroundColor.toLowerCase();
      if ("transparent" === backgroundColor || "rgba(0, 0, 0, 0)" === backgroundColor) {
        return true;
      }
    }

    const computedStyle = getComputedStyle(el);
    if (isBackgroundImageNone(computedStyle)) {
      return null;
    }

    const treeWalker = document.createTreeWalker(
      el, NodeFilter.SHOW_ALL,
      {
        acceptNode: (n) => {
          if (n instanceof Text) {
            if (n.nodeValue && n.nodeValue.trim().length > 0) {
              return NodeFilter.FILTER_ACCEPT;
            } else {
              return NodeFilter.FILTER_SKIP;
            }
          }
          if (n instanceof Element) {
            const computedStyle = getComputedStyle(n);
            if (!isBackgroundImageNone(computedStyle) || !isBackgroundColorTransparent(computedStyle)) {
              return NodeFilter.FILTER_REJECT;
            }
          }
          return NodeFilter.FILTER_SKIP;
        }
      }, false);

    const ret = [];
    let node = treeWalker.nextNode();
    while (node) {
      ret.push(node.nodeValue);
      node = treeWalker.nextNode();
    }

    return ret;
  };

  let baseUrl = null;
  const baseNode = document.head.querySelector("base");
  if (baseNode) {
    baseUrl = baseNode.href;
  }
  if (!baseUrl) {
    baseUrl = location.href;
  }

  const makeFull = function(url, baseUrl) {
    if (! url) {
      return null;
    }

    try {
      return new URL(url, baseUrl).href;
    } catch (ex) {
      return null;
    }
  };

  const makeHash = function(el) {
    return {
      xpath: fullXPath(el),
      cssPath: cssPath(el),
      tagName: el.nodeName.toLowerCase(),
      rect: sliceMap(el.getBoundingClientRect(), [ "x", "y", "width", "height", "left", "top", "right", "bottom" ]),
      style: sliceMap(el.style, [ "background", "backgroundColor", "backgroundImage", "color", "fontSize", "opacity" ]),
      computedStyle: sliceMap(
        getComputedStyle(el),
        [ "background", "backgroundColor", "backgroundImage", "color", "fontSize", "opacity" ]
      ),
      href: makeFull(el.href, baseUrl),
      texts: texts(el),
      descendantTextsWithBGImage: descendantTextsWithBGImage(el)
    };
  };

  const ret = Array.from(document.body.querySelectorAll(selector), (el) => makeHash(el));
  ret.unshift(makeHash(document.body));
  ret.unshift(makeHash(document.documentElement));

  return ret;
})("*");
