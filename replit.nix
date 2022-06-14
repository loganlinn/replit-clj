{ pkgs }: with pkgs; {
    deps = [
      babashka
      clojure
      clojure-lsp
      clj-kondo
    ];
}